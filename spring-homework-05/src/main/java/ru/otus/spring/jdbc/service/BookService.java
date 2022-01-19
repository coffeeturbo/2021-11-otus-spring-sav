package ru.otus.spring.jdbc.service;

public interface BookService {
    String createBook(long authorId, String name, String genresIds);

    String getAllBooks();

    String getBookById(long id);

    String updateBook(long bookId, long authorId, String name, String genresIds);

    String deleteBook(long bookId);
}
