

CREATE TABLE IF NOT EXISTS shard_routing (
    id    INT         PRIMARY KEY,
    physical_node_name  VARCHAR NOT NULL,
    physical_node_host  VARCHAR(100) NOT NULL,
    physical_node_port  INT         NOT NULL,
    status              VARCHAR(20) NOT NULL DEFAULT 'active',
    created_at          TIMESTAMPTZ DEFAULT NOW(),
    updated_at          TIMESTAMPTZ DEFAULT NOW()
    );


INSERT INTO shard_routing
(id, physical_node_name, physical_node_host, physical_node_port, status)
VALUES
    -- node 1 hosts logical shards 0-3
    (0,  'shard1', '127.0.0.1', 5431, 'active'),
    (1,  'shard1', '127.0.0.1', 5431, 'active'),
    (2,  'shard1', '127.0.0.1', 5431, 'active'),
    (3,  'shard1', '127.0.0.1', 5431, 'active'),

    -- node 2 hosts logical shards 4-7
    (4,  'shard2', '127.0.0.1', 5435, 'active'),
    (5,  'shard2', '127.0.0.1', 5435, 'active'),
    (6,  'shard2', '127.0.0.1', 5435, 'active'),
    (7,  'shard2', '127.0.0.1', 5435, 'active'),

    -- node 3 hosts logical shards 8-11
    (8,  'shard3', '127.0.0.1', 5434, 'active'),
    (9,  'shard3', '127.0.0.1', 5434, 'active'),
    (10, 'shard3', '127.0.0.1', 5434, 'active'),
    (11, 'shard3', '127.0.0.1', 5434, 'active');

