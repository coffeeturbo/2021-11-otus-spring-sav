package ru.otus.spring.mvc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.mvc.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> getUserByLogin(String login);
}
