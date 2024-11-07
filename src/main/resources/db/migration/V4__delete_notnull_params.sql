DROP TABLE IF EXISTS user_entity;

CREATE TABLE user_entity
(
    id         BIGINT PRIMARY KEY NOT NULL,
    state      VARCHAR(50),
    area       VARCHAR(50),
    is_notify  BOOLEAN DEFAULT FALSE,
    experience VARCHAR(50),
    salary     DOUBLE PRECISION,
    job_title  VARCHAR(50)
);