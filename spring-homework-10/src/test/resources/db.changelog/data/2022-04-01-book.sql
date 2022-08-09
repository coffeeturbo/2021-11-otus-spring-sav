
--liquibase formatted sql
--changeset vsavushkin:2022-04-01-book context:test
TRUNCATE book RESTART IDENTITY CASCADE;

INSERT INTO book (name, author_id)
VALUES ('Мартин Иден', 1);
INSERT INTO book (name, author_id)
VALUES ('Любовь к жизни', 1);
INSERT INTO book (name, author_id)
VALUES ('Сами боги', 2);
INSERT INTO book (name, author_id)
VALUES ('Основание', 2);
INSERT INTO book (name, author_id)
VALUES ('Стальные пещеры', 2);


INSERT INTO book (name, author_id)
VALUES ('Капитал', 3);
INSERT INTO book (name, author_id)
VALUES ('Коммунизм', 3);
INSERT INTO book (name, author_id)
VALUES ('Социализм', 3);