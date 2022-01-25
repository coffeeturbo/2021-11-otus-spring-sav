package ru.otus.spring.jdbc.repository;

import ru.otus.spring.jdbc.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    long count();
    Author save(Author author);
    void deleteById(long id);
    Optional<Author> getById(long id);
    List<Author> getAll();
}
