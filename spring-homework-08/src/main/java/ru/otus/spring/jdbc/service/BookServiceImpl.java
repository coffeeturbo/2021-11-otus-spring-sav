package ru.otus.spring.jdbc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.exception.DataAccessException;
import ru.otus.spring.jdbc.formatter.BookFormatter;
import ru.otus.spring.jdbc.repository.AuthorRepository;
import ru.otus.spring.jdbc.repository.BookRepository;
import ru.otus.spring.jdbc.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookFormatter bookFormatter;

    @Override
    public String save(String bookId, String authorId, String name, String genresIds) {
        var rsl = "";
        try {
            Iterable<String> ids = Arrays.asList(genresIds.split(","));

            var genres = genreRepository.findAllById(ids);

            var book = bookBuilder(bookId, authorId, name, genres);
            rsl = saveBook(book);

        } catch (DataAccessException e) {
            log.error("create book exception occurred", e);
        }
        return rsl;
    }

    @Override
    public String getAllBooks() {
        return bookRepo.findAll().stream()
                .map(bookFormatter::format)
                .collect(Collectors.joining(";" + System.lineSeparator()));
    }

    @Override
    public String getBookById(String id) {
        return bookFormatter.format(bookRepo.findById(id).orElseThrow());
    }

    @Override
    public String deleteBook(String bookId) {
        bookRepo.deleteById(bookId);
        return String.format("Book %s was deleted", bookId);
    }

    private String saveBook(Book book) throws DataAccessException {
        bookRepo.save(book);
        return bookFormatter.format(book);
    }

    private Book bookBuilder(String bookId, String authorId, String name, List<Genre> genres) {
        var author = authorRepository.findById(authorId).orElseThrow();
        return new Book(bookId, author, name, genres);
    }

}
