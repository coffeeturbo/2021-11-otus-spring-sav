package ru.otus.spring.jdbc.dao;

import ru.otus.spring.jdbc.domain.Book;

import java.util.List;

public interface BookDao {
    int count();
    long insert(Book book);
    void update(Book book);
    void deleteById(long id);
    Book getById(long id);
    List<Book> getAll();
    List<Book> getBooksByGenreId(long genreId);
    List<Book> getBooksByAuthorId(long authorId);
}
