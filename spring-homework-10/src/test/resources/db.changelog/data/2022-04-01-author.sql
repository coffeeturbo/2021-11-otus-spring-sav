
--liquibase formatted sql
--changeset vsavushkin:2022-04-01-author context:test
TRUNCATE author RESTART IDENTITY CASCADE;
INSERT INTO author (first_name, last_name)
VALUES ('Jack2', 'London');
INSERT INTO author (first_name, last_name)
VALUES ('Isiac', 'Asimov');
INSERT INTO author (first_name, last_name)
VALUES ('Carl', 'Marks');
