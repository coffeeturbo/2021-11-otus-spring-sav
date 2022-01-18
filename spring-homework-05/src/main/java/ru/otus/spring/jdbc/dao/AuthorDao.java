package ru.otus.spring.jdbc.dao;

import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.exception.DataAccessException;

import java.util.List;

public interface AuthorDao {
    int count();
    long insert(Author author) throws DataAccessException;
    void update(Author author);
    void deleteById(long id);
    Author getById(long id);
    List<Author> getAll();
}
