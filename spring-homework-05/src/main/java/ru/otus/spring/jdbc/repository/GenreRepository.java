package ru.otus.spring.jdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    void deleteById(long id);

    Optional<Genre> getById(long id);
    @Override
    List<Genre> findAll();
}
