package ru.otus.spring.jdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.jdbc.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    void deleteById(long id);

    Optional<Author> getById(long id);

    @Override
    List<Author> findAll();
}
