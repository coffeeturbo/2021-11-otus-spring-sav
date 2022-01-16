DROP TABLE IF EXISTS author;
CREATE TABLE author (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

DROP TABLE IF EXISTS genre;
CREATE TABLE genre (
   id BIGSERIAL PRIMARY KEY,
   name VARCHAR(255)
);

DROP TABLE IF EXISTS book;
CREATE TABLE book (
   id BIGSERIAL PRIMARY KEY,
   author_id BIGINT REFERENCES author(id) ON DELETE CASCADE,
   name VARCHAR(255)
);

DROP TABLE IF EXISTS book_genre;
CREATE TABLE book_genre (
   book_id BIGINT REFERENCES book(id) ON DELETE CASCADE,
   genre_id BIGINT REFERENCES genre(id) ON DELETE CASCADE
);




