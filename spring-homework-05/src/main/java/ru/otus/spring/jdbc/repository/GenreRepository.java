package ru.otus.spring.jdbc.repository;

import ru.otus.spring.jdbc.domain.Genre;

import java.util.List;

public interface GenreRepository {
    int count();
    long insert(Genre person);
    void update(Genre person);
    void deleteById(long id);
    Genre getById(long id);
    List<Genre> getAll();
    List<Genre> getGenresByBookId(long bookId);
}
