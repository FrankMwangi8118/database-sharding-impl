CREATE TABLE gym
(
    id         BIGINT NOT NULL,
    name       VARCHAR(255),
    location   VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_gym PRIMARY KEY (id)
);