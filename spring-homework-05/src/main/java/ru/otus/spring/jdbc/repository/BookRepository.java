package ru.otus.spring.jdbc.repository;

import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.exception.DataAccessException;

import java.util.List;

public interface BookRepository {
    int count();
    long insert(Book book) throws DataAccessException;
    void update(Book book);
    void deleteById(long id);
    Book getById(long id);
    List<Book> getAll();
    List<Book> getBooksByGenreId(long genreId);
    List<Book> getBooksByAuthorId(long authorId);
}
