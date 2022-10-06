--liquibase formatted sql
--changeset savushkin:2022-01-20--005-comment

DROP TABLE if EXISTS `users`;
CREATE TABLE `users`
(
    id   BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    roles varchar(255) NOT NULL
);
