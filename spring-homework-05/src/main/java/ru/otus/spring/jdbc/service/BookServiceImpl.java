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

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookFormatter bookFormatter;

    @Override
    public String createBook(long authorId, String name, String genresIds) {

        var rsl = "";
        try {
            rsl = save(bookBuilder(0, authorId, name, genresIds));
        } catch (DataAccessException e) {
            log.error("create book exception occurred", e);
        }
        return rsl;
    }

    @Override
    public String getAllBooks() {
        return bookRepository.getAll().stream()
                .map(bookFormatter::format)
                .collect(Collectors.joining("; "));
    }

    @Override
    public String getBookById(long id) {
        return bookFormatter.format(bookRepository.getById(id));
    }

    @Override
    public String updateBook(long bookId, long authorId, String name, String genresIds) {
        var rsl = "";
        try {
            rsl = save(bookBuilder(bookId, authorId, name, genresIds));
        } catch (DataAccessException e) {
            log.error("create book exception occurred", e);
        }
        return rsl;
    }

    @Override
    public String deleteBook(long bookId) {
        bookRepository.deleteById(bookId);
        return String.format("Book %s was deleted", bookId);
    }

    private String save(Book book) throws DataAccessException {
        if (book.getId() == 0) {
            var id = bookRepository.insert(book);
            book = bookRepository.getById(id);
        } else {
            bookRepository.update(book);
        }
        return bookFormatter.format(book);
    }

    private Book bookBuilder(long bookId, long authorId, String name, String genresIds) {
        var author = authorRepository.getById(authorId);

        List<Genre> genres = Arrays.stream(genresIds.split(","))
                .map(id -> genreRepository.getById(Long.parseLong(id)))
                .collect(Collectors.toList());

        return new Book(bookId, author.get(), name, genres);
    }

}
