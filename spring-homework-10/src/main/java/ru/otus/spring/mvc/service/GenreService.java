package ru.otus.spring.mvc.service;

import ru.otus.spring.mvc.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Genre create(Genre genre);
    Genre update(Genre genre);
    void deleteById(long id);
    List<Genre> findAllGenres();

    Optional<Genre> findById(long id);

    List<Genre> findByIds(long[] ids);
}
