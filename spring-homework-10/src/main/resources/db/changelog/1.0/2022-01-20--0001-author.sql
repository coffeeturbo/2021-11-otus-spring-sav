--liquibase formatted sql
--changeset vsavushkin:2022-01-20--001-author context:prod

DROP TABLE IF EXISTS author;
CREATE TABLE author
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL
);
