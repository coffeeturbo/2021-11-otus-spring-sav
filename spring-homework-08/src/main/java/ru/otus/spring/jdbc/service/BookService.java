package ru.otus.spring.jdbc.service;

public interface BookService {
    String save(String bookId, String authorId, String name, String genresIds);

    String getAllBooks();

    String getBookById(String id);

    String deleteBook(String bookId);
}
