package ru.otus.spring.jdbc.repository;

import ru.otus.spring.jdbc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long count();
    Book save(Book book);
    void deleteById(long id);
    Optional<Book> getById(long id);
    List<Book> getAll();
}
