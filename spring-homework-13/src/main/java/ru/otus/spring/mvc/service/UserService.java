package ru.otus.spring.mvc.service;

import ru.otus.spring.mvc.domain.User;


public interface UserService {
    User getUserByLogin(String login);
}
