--liquibase formatted sql
--changeset savushkin:2022-01-20--005-comment

DROP TABLE if EXISTS comment;
CREATE TABLE comment
(
    id   BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL REFERENCES book(id) ON DELETE CASCADE,
    text VARCHAR(255) NOT NULL
);
