package ru.otus.spring.mvc.service;

import ru.otus.spring.mvc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book createBook(Book book);

    List<Book> getAllBooks();

    Optional<Book> getBookById(long id);

    Book updateBook(Book book);

    void deleteBook(long bookId);
}
