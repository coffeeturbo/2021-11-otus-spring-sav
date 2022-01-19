package ru.otus.spring.jdbc.dao;

import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.exception.DataAccessException;

import java.util.List;

public interface GenreDao {
    int count();
    long insert(Genre person) throws DataAccessException;
    void update(Genre person);
    void deleteById(long id);
    Genre getById(long id);
    List<Genre> getAll();
    List<Genre> getGenresByBookId(long bookId);
}
