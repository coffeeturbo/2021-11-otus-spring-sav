package ru.otus.spring.jdbc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.List;


public interface GenreRepository extends MongoRepository<Genre, String> {

    List<Genre> findAllById(Iterable<String> genreIds);
}
