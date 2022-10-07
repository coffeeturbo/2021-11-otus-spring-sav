package ru.otus.spring.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.mvc.domain.User;
import ru.otus.spring.mvc.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;


    @Override
    public User getUserByLogin(String login) {

        return repository.getUserByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("user with login:" + login + "not founded"));
    }
}
