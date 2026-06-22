package com.codify.shardingimpl.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ShardRoutingConfigLoader {

    private static final int LOGICAL_SHARDS = 10;
    private final AtomicLong shardCounter = new AtomicLong(0);
    private final Map<Integer, DataSource> shardMap = new HashMap<>();

    private final Map<String, DataSource> nodeMap;

    public ShardRoutingConfigLoader(
            @Qualifier("metaDataSource") DataSource metaDataSource,
            @Qualifier("shard1DataSource") DataSource shard1DataSource,
            @Qualifier("shard2DataSource") DataSource shard2DataSource,
            @Qualifier("shard3DataSource") DataSource shard3DataSource
    ) {

/**
 * key (node_name) : value(dataSource)
 */
        nodeMap = new HashMap<>();
        nodeMap.put("shard1", shard1DataSource);
        nodeMap.put("shard2", shard2DataSource);
        nodeMap.put("shard3", shard3DataSource);

        loadRoutingTable(metaDataSource);
    }

    private void loadRoutingTable(DataSource dataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT id, physical_node_name " +
                        "FROM shard_routing WHERE status = 'active'"
        );

        /**
         *key(logical_id,respective DataSource
         *key(0),value(shard1)
         */
        for (Map<String, Object> row : rows) {
            Integer id = (Integer) row.get("id");
            String physicalNodeName = (String) row.get("physical_node_name");


            DataSource ds = nodeMap.get(physicalNodeName);

            if (!Objects.isNull(ds)) {
                shardMap.put(id, ds);
            }

        }
    }

    public Long generateLogicalShardId() {
        return shardCounter.getAndIncrement();
    }


}
