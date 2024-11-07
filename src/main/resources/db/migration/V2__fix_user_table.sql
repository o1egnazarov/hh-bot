DROP TABLE IF EXISTS user_entity;

CREATE TABLE user_entity
(
    id         BIGINT PRIMARY KEY NOT NULL,
    state      VARCHAR(255),
    area       VARCHAR(50)        NOT NULL,
    is_notify  BOOLEAN DEFAULT FALSE,
    experience VARCHAR(50)        NOT NULL,
    salary     DOUBLE PRECISION   NOT NULL,
    job_title  VARCHAR(50)        NOT NULL
);