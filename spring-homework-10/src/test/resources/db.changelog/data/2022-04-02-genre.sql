
--liquibase formatted sql
--changeset vsavushkin:2022-04-02-genre context:test
TRUNCATE genre RESTART IDENTITY CASCADE;

INSERT INTO genre (name)
VALUES ('comedy');
INSERT INTO genre (name)
VALUES ('drama');
INSERT INTO genre (name)
VALUES ('science');
