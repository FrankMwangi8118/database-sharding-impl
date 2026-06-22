package com.codify.shardingimpl.util;

import com.codify.shardingimpl.config.ShardRoutingConfigLoader;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SnowFlakeIdGeneratorTest {

    private static final long EPOCH = 1704067200000L;
    private static final long TEST_TIMESTAMP = 123_456L;
    private static final long SEQUENCE_MASK = (1L << 10) - 1;
    private static final long LOGICAL_SHARD_MASK = (1L << 13) - 1;

    @Test
    void generateIdIncludesTimestampShardAndSequence() {
        ShardRoutingConfigLoader shardRoutingConfigLoader = mock(ShardRoutingConfigLoader.class);
        when(shardRoutingConfigLoader.generateLogicalShardId()).thenReturn(3L);
        AtomicLong currentTimeMillis = new AtomicLong(EPOCH + TEST_TIMESTAMP);

        SnowFlakeIdGenerator generator = new SnowFlakeIdGenerator(
                shardRoutingConfigLoader,
                currentTimeMillis::get
        );

        Long firstId = generator.generateId();
        Long secondId = generator.generateId();

        assertAll(
                () -> assertNotEquals(firstId, secondId),
                () -> assertEquals(TEST_TIMESTAMP, extractTimestamp(firstId)),
                () -> assertEquals(TEST_TIMESTAMP, extractTimestamp(secondId)),
                () -> assertEquals(3L, extractLogicalShardId(firstId)),
                () -> assertEquals(3L, extractLogicalShardId(secondId)),
                () -> assertEquals(0L, extractSequence(firstId)),
                () -> assertEquals(1L, extractSequence(secondId))
        );
    }

    @Test
    void generateIdResetsSequenceWhenMillisChanges() {
        ShardRoutingConfigLoader shardRoutingConfigLoader = mock(ShardRoutingConfigLoader.class);
        when(shardRoutingConfigLoader.generateLogicalShardId()).thenReturn(4L);
        AtomicLong currentTimeMillis = new AtomicLong(EPOCH + TEST_TIMESTAMP);

        SnowFlakeIdGenerator generator = new SnowFlakeIdGenerator(
                shardRoutingConfigLoader,
                currentTimeMillis::get
        );

        Long firstId = generator.generateId();
        currentTimeMillis.incrementAndGet();
        Long secondId = generator.generateId();

        assertAll(
                () -> assertEquals(TEST_TIMESTAMP, extractTimestamp(firstId)),
                () -> assertEquals(TEST_TIMESTAMP + 1, extractTimestamp(secondId)),
                () -> assertEquals(0L, extractSequence(firstId)),
                () -> assertEquals(0L, extractSequence(secondId))
        );
    }

    @Test
    void generateIdRejectsShardIdsThatDoNotFitInShardBits() {
        ShardRoutingConfigLoader shardRoutingConfigLoader = mock(ShardRoutingConfigLoader.class);
        when(shardRoutingConfigLoader.generateLogicalShardId()).thenReturn(LOGICAL_SHARD_MASK + 1);
        AtomicLong currentTimeMillis = new AtomicLong(EPOCH + TEST_TIMESTAMP);

        SnowFlakeIdGenerator generator = new SnowFlakeIdGenerator(
                shardRoutingConfigLoader,
                currentTimeMillis::get
        );

        assertThrows(IllegalStateException.class, generator::generateId);
    }

    private static long extractTimestamp(long id) {
        return id >> 23;
    }

    private static long extractLogicalShardId(long id) {
        return (id >> 10) & LOGICAL_SHARD_MASK;
    }

    private static long extractSequence(long id) {
        return id & SEQUENCE_MASK;
    }
}
