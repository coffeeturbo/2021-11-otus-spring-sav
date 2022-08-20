--liquibase formatted sql
--changeset vsavushkin:2022-01-20--002-genre context:prod

DROP TABLE IF EXISTS genre;
CREATE TABLE genre
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
