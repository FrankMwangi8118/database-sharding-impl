package com.codify.shardingimpl.util;

public final class SnowflakeIdCodec {
    static final int SEQUENCE_BITS = 10;
    static final int LOGICAL_SHARD_BITS = 13;
    static final int LOGICAL_SHARD_SHIFT = SEQUENCE_BITS;
    static final int TIMESTAMP_SHIFT = LOGICAL_SHARD_BITS + SEQUENCE_BITS;
    static final long SEQUENCE_MASK = (1L << SEQUENCE_BITS) - 1;
    static final long LOGICAL_SHARD_MASK = (1L << LOGICAL_SHARD_BITS) - 1;

    private SnowflakeIdCodec() {
    }

    public static long buildId(long timestampMillis, long logicalShardId, long sequence) {
        return (timestampMillis << TIMESTAMP_SHIFT)
                | (logicalShardId << LOGICAL_SHARD_SHIFT)
                | sequence;
    }

    public static Long extractShardId(long id) {
        return (id >> LOGICAL_SHARD_SHIFT) & LOGICAL_SHARD_MASK;
    }

    public static long maxLogicalShardId() {
        return LOGICAL_SHARD_MASK;
    }
}
