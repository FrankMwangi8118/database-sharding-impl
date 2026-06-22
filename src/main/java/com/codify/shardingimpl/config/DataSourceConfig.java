package com.codify.shardingimpl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    /**
     * more of routing and logical sharding
     */
    @Bean(name = "metaDataSource")
    @ConfigurationProperties("spring.datasource.meta")
    public DataSource metaDataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * Load shard datasource
     */

    @Bean(name = "shard1DataSource")
    @ConfigurationProperties("spring.datasource.shard1")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "shard2DataSource")
    @ConfigurationProperties("spring.datasource.shard2")
    public DataSource shard2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "shard3DataSource")
    @ConfigurationProperties("spring.datasource.shard3")
    public DataSource shard3DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource routingDataSource(
            DataSource metaDataSource,
            DataSource shard1DataSource,
            DataSource shard2DataSource,
            DataSource shard3DataSource
    ) {
        Map<Object, Object> targets = new HashMap<>();
        targets.put("meta", metaDataSource);
        targets.put("shard1", shard1DataSource);
        targets.put("shard2", shard2DataSource);
        targets.put("shard3", shard3DataSource);

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(targets);
        routingDataSource.setDefaultTargetDataSource(metaDataSource);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
}
