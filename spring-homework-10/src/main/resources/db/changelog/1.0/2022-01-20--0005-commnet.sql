--liquibase formatted sql
--changeset vsavushkin:2022-01-20--005-comment context:prod

DROP TABLE if EXISTS comment;
CREATE TABLE comment
(
    id   BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL REFERENCES book(id) ON DELETE CASCADE,
    text VARCHAR(255) NOT NULL
);
