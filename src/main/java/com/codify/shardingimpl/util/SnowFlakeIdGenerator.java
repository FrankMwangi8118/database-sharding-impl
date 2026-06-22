package com.codify.shardingimpl.util;

import com.codify.shardingimpl.config.ShardRoutingConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.LongSupplier;

@Service
public class SnowFlakeIdGenerator {
    private static final long EPOCH = 1704067200000L;

    private final ShardRoutingConfigLoader shardRoutingConfigLoader;
    private final LongSupplier currentTimeMillis;
    private long lastMillis = -1L;
    private long sequence = 0L;

    @Autowired
    public SnowFlakeIdGenerator(ShardRoutingConfigLoader shardRoutingConfigLoader) {
        this(shardRoutingConfigLoader, System::currentTimeMillis);
    }

    SnowFlakeIdGenerator(ShardRoutingConfigLoader shardRoutingConfigLoader, LongSupplier currentTimeMillis) {
        this.shardRoutingConfigLoader = shardRoutingConfigLoader;
        this.currentTimeMillis = currentTimeMillis;
    }

    public Long generateId() {
        return nextId();
    }

    private long generateLogicalShardId() {
        return shardRoutingConfigLoader.generateLogicalShardId();
    }

    private synchronized long nextId() {
        long currentMillis = currentTimestampMillis();

        if (currentMillis < lastMillis) {
            throw new IllegalStateException("Clock moved backwards. halting to generate id.");
        }

        if (currentMillis == lastMillis) {
            sequence = (sequence + 1) & SnowflakeIdCodec.SEQUENCE_MASK;
            if (sequence == 0) {
                currentMillis = waitUntilNextMillis(lastMillis);
            }
        } else {
            sequence = 0L;
        }

        lastMillis = currentMillis;
        long logicalShardId = generateLogicalShardId();
        if (logicalShardId < 0 || logicalShardId > SnowflakeIdCodec.maxLogicalShardId()) {
            throw new IllegalStateException("Logical shard id must be between 0 and " + SnowflakeIdCodec.maxLogicalShardId());
        }

        return SnowflakeIdCodec.buildId(currentMillis, logicalShardId, sequence);
    }

    private long currentTimestampMillis() {
        return currentTimeMillis.getAsLong() - EPOCH;
    }

    private long waitUntilNextMillis(long previousMillis) {
        long currentMillis = currentTimestampMillis();
        while (currentMillis <= previousMillis) {
            Thread.onSpinWait();
            currentMillis = currentTimestampMillis();
        }
        return currentMillis;
    }

    public Long extractShardId(long id) {
        return SnowflakeIdCodec.extractShardId(id);
    }
}
