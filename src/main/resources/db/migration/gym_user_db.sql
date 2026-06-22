CREATE TABLE gym_users
(
    id                BIGINT NOT NULL,
    gym_id            BIGINT,
    full_name         VARCHAR(255),
    email             VARCHAR(255),
    phone_number      VARCHAR(255),
    membership_plan   VARCHAR(255),
    membership_status VARCHAR(255),
    city              VARCHAR(255),
    country           VARCHAR(255),
    joined_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_gym_users PRIMARY KEY (id)
);

ALTER TABLE gym_users
    ADD CONSTRAINT FK_GYM_USERS_ON_GYM FOREIGN KEY (gym_id) REFERENCES gym (id);