package ru.otus.spring.jdbc.dao;

import ru.otus.spring.jdbc.domain.Author;

import java.util.List;

public interface AuthorDao {
    int count();
    long insert(Author author);
    void update(Author author);
    void deleteById(long id);
    Author getById(long id);
    List<Author> getAll();
}
