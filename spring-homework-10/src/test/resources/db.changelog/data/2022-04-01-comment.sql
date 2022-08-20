
--liquibase formatted sql
--changeset vsavushkin:2022-04-01-comment context:test
TRUNCATE comment RESTART IDENTITY CASCADE;

INSERT INTO comment (text, book_id)
VALUES ('Ужасная книга читал до упада', 1);
INSERT INTO comment (text, book_id)
VALUES ('не читал', 1);
INSERT INTO comment (text, book_id)
VALUES ('плохая книга', 1);
INSERT INTO comment (text, book_id)
VALUES ('Так себе', 2);
INSERT INTO comment (text, book_id)
VALUES ('читать не буду', 3);
