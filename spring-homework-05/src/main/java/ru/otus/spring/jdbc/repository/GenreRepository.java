package ru.otus.spring.jdbc.repository;

import ru.otus.spring.jdbc.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    long count();
    Genre save(Genre genre);
    void deleteById(long id);
    Optional<Genre> getById(long id);
    List<Genre> getAll();
}
