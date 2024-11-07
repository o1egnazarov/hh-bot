CREATE TABLE IF NOT EXISTS user_entity
(
    id         SERIAL PRIMARY KEY NOT NULL,
    state      VARCHAR(255),
    area       VARCHAR(50)        NOT NULL,
    is_notify  BOOLEAN DEFAULT FALSE,
    experience VARCHAR(50)        NOT NULL,
    salary     NUMERIC(10, 2)     NOT NULL,
    job_title  VARCHAR(50)        NOT NULL
);