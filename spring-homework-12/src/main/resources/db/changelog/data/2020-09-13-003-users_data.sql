
--liquibase formatted sql

--changeset vsavushkin:2022-09-13-002-users-data
-- login/pass
INSERT INTO `users` (login, password, salt)
VALUES ('login', '$2a$12$DOE99TLiTg3G0XcdH5/ZkeqQqTWwjHw.J3WiSBDjqKNkW7h84K6xG', 'sault');
