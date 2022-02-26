package ru.otus.spring.mvc.service;

import ru.otus.spring.mvc.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> getAllAuthors();
    Optional<Author> findAuthorById(long id);
}
