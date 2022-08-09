--liquibase formatted sql
--changeset vsavushkin:2022-01-20--003-book context:prod

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    id        BIGSERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL REFERENCES author (id) ON DELETE CASCADE,
    name      VARCHAR(255) NOT NULL
);

--changeset vsavushkin:2022-01-20--004-book-genre

DROP TABLE IF EXISTS book_genre;
CREATE TABLE book_genre
(
    book_id  BIGINT REFERENCES book (id) ON DELETE CASCADE,
    genre_id BIGINT REFERENCES genre (id) ON DELETE CASCADE
);
