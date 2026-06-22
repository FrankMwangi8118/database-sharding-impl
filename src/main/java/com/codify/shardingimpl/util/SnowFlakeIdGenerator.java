package com.codify.shardingimpl.util;

import com.codify.shardingimpl.config.ShardRoutingConfigLoader;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SnowFlakeIdGenerator {
    private final ShardRoutingConfigLoader shardRoutingConfigLoader;
    private final Long EPOCH = 1704067200000L;
    long now = System.currentTimeMillis() - EPOCH;
    private Long lastms = -1L;
    private Long sequence = 0L;

    public Long generateIds() {
        return nextId();
    }

    private Long generateLogicalShardId() {
        return shardRoutingConfigLoader.generateLogicalShardId();
    }


    private synchronized long nextId() {

        if (now == lastms) {
            sequence = (sequence + 1) & 1023;
            if (sequence == 0) {

                while (now <= lastms) {
                    now = System.currentTimeMillis() - EPOCH;
                }

            } else {
                sequence = 0L;
            }
            lastms = now;


        }
        return (now << 23)
                | (generateLogicalShardId() << 10)
                | sequence;
    }

    public Long extractShardId(long id) {
        return (Long) ((id >> 10) & 8191);
    }
}
