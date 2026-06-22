package com.codify.shardingimpl.util;

import com.codify.shardingimpl.config.DataSourceContextHolder;
import com.codify.shardingimpl.config.ShardRoutingConfigLoader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataSourceContextSetter {
    private final ShardRoutingConfigLoader shardRoutingConfigLoader;

    public void setDataSource(String name){
        DataSourceContextHolder.use(name);
    }

}
