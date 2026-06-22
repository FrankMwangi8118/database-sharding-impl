package com.codify.shardingimpl.util;

import com.codify.shardingimpl.config.ShardRoutingConfigLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SnowFlakeIdGeneratorTest {

    private static final long TEST_TIMESTAMP = 123_456L;
    private static final long LOGICAL_SHARD_MASK = (1L << 13) - 1;

    @Test
    void generateIdsPrintsGeneratedSnowflakeIds() {
        ShardRoutingConfigLoader shardRoutingConfigLoader = mock(ShardRoutingConfigLoader.class);
        when(shardRoutingConfigLoader.generateLogicalShardId()).thenReturn(3L, 4L, 5L);

        SnowFlakeIdGenerator generator = new SnowFlakeIdGenerator(
                shardRoutingConfigLoader,
                TEST_TIMESTAMP,
                -1L,
                0L
        );

        Long firstId = generator.generateIds();
        Long secondId = generator.generateIds();
        Long thirdId = generator.generateIds();

        System.out.printf(
                "Generated snowflake IDs:%n%s%n%s%n%s%n",
                firstId,
                secondId,
                thirdId
        );

        assertAll(
                () -> assertTrue(firstId > 0),
                () -> assertTrue(secondId > 0),
                () -> assertTrue(thirdId > 0),
                () -> assertNotEquals(firstId, secondId),
                () -> assertNotEquals(secondId, thirdId),
                () -> assertEquals(3L, extractLogicalShardId(firstId)),
                () -> assertEquals(4L, extractLogicalShardId(secondId)),
                () -> assertEquals(5L, extractLogicalShardId(thirdId))
        );
    }

    private static long extractLogicalShardId(long id) {
        return (id >> 10) & LOGICAL_SHARD_MASK;
    }
}
