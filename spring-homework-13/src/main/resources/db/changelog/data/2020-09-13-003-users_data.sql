--liquibase formatted sql

--changeset vsavushkin:2022-09-13-002-users-data
-- login/pass
INSERT INTO `users` (login, password, salt, roles)
VALUES ('login', '$2a$12$DOE99TLiTg3G0XcdH5/ZkeqQqTWwjHw.J3WiSBDjqKNkW7h84K6xG', 'sault', 'AUTHORIZED');

-- admin/pass
INSERT INTO `users` (login, password, salt, roles)
VALUES ('admin', '$2a$12$DOE99TLiTg3G0XcdH5/ZkeqQqTWwjHw.J3WiSBDjqKNkW7h84K6xG', 'sault', 'ADMIN');

-- manager/pass
INSERT INTO `users` (login, password, salt, roles)
VALUES ('manager', '$2a$12$DOE99TLiTg3G0XcdH5/ZkeqQqTWwjHw.J3WiSBDjqKNkW7h84K6xG', 'sault', 'MANAGER');
