
--liquibase formatted sql

--changeset vsavushkin:2022-01-20-001-data
INSERT INTO author (`first_name`, `last_name`)
VALUES ('Jack', 'London');
INSERT INTO author (`first_name`, `last_name`)
VALUES ('Isiac', 'Asimov');
INSERT INTO author (`first_name`, `last_name`)
VALUES ('Carl', 'Marks');


INSERT INTO genre (`name`)
VALUES ('comedy');
INSERT INTO genre (`name`)
VALUES ('drama');
INSERT INTO genre (`name`)
VALUES ('science');


INSERT INTO book (`name`, `author_id`)
VALUES ('Мартин Иден', 1);
INSERT INTO book (`name`, `author_id`)
VALUES ('Любовь к жизни', 1);
INSERT INTO book (`name`, `author_id`)
VALUES ('Сами боги', 2);
INSERT INTO book (`name`, `author_id`)
VALUES ('Основание', 2);
INSERT INTO book (`name`, `author_id`)
VALUES ('Стальные пещеры', 2);


INSERT INTO book (`name`, `author_id`)
VALUES ('Капитал', 3);
INSERT INTO book (`name`, `author_id`)
VALUES ('Коммунизм', 3);
INSERT INTO book (`name`, `author_id`)
VALUES ('Социализм', 3);


INSERT INTO book_genre (`book_id`, `genre_id`)
VALUES (1, 1);
INSERT INTO book_genre (`book_id`, `genre_id`)
VALUES (1, 2);
INSERT INTO book_genre (`book_id`, `genre_id`)
VALUES (2, 2);


INSERT INTO book_genre (`book_id`, `genre_id`)
VALUES (6, 3);
INSERT INTO book_genre (`book_id`, `genre_id`)
VALUES (7, 3);
INSERT INTO book_genre (`book_id`, `genre_id`)
VALUES (8, 3);

INSERT INTO comment (`text`, `book_id`)
VALUES ('Ужасная книга читал до упада', 1);
INSERT INTO comment (`text`, `book_id`)
VALUES ('не читал', 1);
INSERT INTO comment (`text`, `book_id`)
VALUES ('плохая книга', 1);
INSERT INTO comment (`text`, `book_id`)
VALUES ('Так себе', 2);
INSERT INTO comment (`text`, `book_id`)
VALUES ('читать не буду', 3);

--changeset stvort:2022-01-20-002-comment-data

INSERT INTO comment (`text`, `book_id`)
VALUES ('Отличная книга читал до упада', 4);
INSERT INTO comment (`text`, `book_id`)
VALUES ('Прочитал', 5);
INSERT INTO comment (`text`, `book_id`)
VALUES ('Нормальная книга', 5);
INSERT INTO comment (`text`, `book_id`)
VALUES ('Зачет', 5);
INSERT INTO comment (`text`, `book_id`)
VALUES ('Крутая книга', 5);