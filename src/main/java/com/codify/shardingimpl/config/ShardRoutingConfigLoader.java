package com.codify.shardingimpl.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class ShardRoutingConfigLoader {

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


    }

}
