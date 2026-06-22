package com.codify.shardingimpl.config;

import com.codify.shardingimpl.util.SnowflakeIdCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ShardRoutingConfigLoader {
    private static final Logger log = LoggerFactory.getLogger(ShardRoutingConfigLoader.class);
    private final AtomicLong shardCounter = new AtomicLong(0);
    private final Map<Long, DataSource> shardMap = new HashMap<>();
    private final List<Long> logicalShardIds = new ArrayList<>();
    private final Map<Long, String> shardNodeMap = new HashMap<>();

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
        shardMap.clear();
        logicalShardIds.clear();

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
            Long id = ((Number) row.get("id")).longValue();
            String physicalNodeName = (String) row.get("physical_node_name");

            DataSource ds = nodeMap.get(physicalNodeName);

            if (ds == null) {
                log.warn("Skipping logical shard {} because physical node '{}' is not configured", id, physicalNodeName);
                continue;
            }

            shardMap.put(id, ds);
            shardNodeMap.put(id, physicalNodeName);
            logicalShardIds.add(id);
            log.info("Loaded logical shard {} -> physical node '{}'", id, physicalNodeName);
        }

        logicalShardIds.sort(Long::compareTo);
        log.info("Loaded {} active logical shards: {}", logicalShardIds.size(), logicalShardIds);
    }

    public Long generateLogicalShardId() {
        if (logicalShardIds.isEmpty()) {
            throw new IllegalStateException("No active logical shards configured");
        }

        int index = Math.floorMod(shardCounter.getAndIncrement(), logicalShardIds.size());
        Long logicalShardId = logicalShardIds.get(index);
        log.info("Generated logical shard id {}", logicalShardId);
        return logicalShardId;
    }

    public DataSource getDatasourceById(Long id) {
        var logicalId = SnowflakeIdCodec.extractShardId(id);
        log.info("Getting datasource by logical shard id {}", logicalId);
        return shardMap.get(logicalId);

    }
    public String getDataSourceNameById(Long id) {
        var logicalId = SnowflakeIdCodec.extractShardId(id);
        var dataSourceName = shardNodeMap.get(logicalId);

        if (dataSourceName == null) {
            throw new IllegalStateException("No datasource found for logical shard " + logicalId);
        }

        return dataSourceName;
    }


}
