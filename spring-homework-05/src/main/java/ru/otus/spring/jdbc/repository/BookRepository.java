package ru.otus.spring.jdbc.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.jdbc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    void deleteById(long id);

    Optional<Book> getById(long id);

    @Override
    List<Book> findAll();
}
