INSERT INTO author (`first_name`, `last_name`) VALUES ('Jack', 'London');


INSERT INTO genre (`name`) VALUES ('comedy');
INSERT INTO genre (`name`) VALUES ('drama');


INSERT INTO book (`name`, `author_id`) VALUES ('Мартин Иден', 1);
INSERT INTO book (`name`, `author_id`) VALUES ('Любовь к жизни', 1);


INSERT INTO book_genre (`book_id`, `genre_id`) VALUES (1, 1);
INSERT INTO book_genre (`book_id`, `genre_id`) VALUES (1, 2);


INSERT INTO author_book (`author_id`, `book_id`) VALUES (1, 1);
INSERT INTO author_book (`author_id`, `book_id`) VALUES (1, 2);
