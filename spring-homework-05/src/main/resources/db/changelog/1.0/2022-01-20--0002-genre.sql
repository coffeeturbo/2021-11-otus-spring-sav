--liquibase formatted sql
--changeset savushkin:2022-01-20--002-genre

DROP TABLE IF EXISTS genre;
CREATE TABLE genre
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
