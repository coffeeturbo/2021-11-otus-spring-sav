package ru.otus.spring.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.jdbc.dao.AuthorDao;
import ru.otus.spring.jdbc.dao.BookDao;
import ru.otus.spring.jdbc.dao.GenreDao;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.formatter.BookFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookFormatter bookFormatter;

    @Override
    public String createBook(long authorId, String name, String genresIds) {
        return save(bookBuilder(0, authorId, name, genresIds));
    }

    @Override
    public String getAllBooks() {
        return bookDao.getAll().stream()
                .map(bookFormatter::format)
                .collect(Collectors.joining("; "));
    }

    @Override
    public String getBookById(long id) {
        return bookFormatter.format(bookDao.getById(id));
    }

    @Override
    public String updateBook(long bookId, long authorId, String name, String genresIds) {
        return save(bookBuilder(bookId, authorId, name, genresIds));
    }

    @Override
    public String deleteBook(long bookId) {
        bookDao.deleteById(bookId);
        return String.format("Book %s was deleted", bookId);
    }

    private String save(Book book) {
        if (book.getId() == 0) {
            var id = bookDao.insert(book);
            book = bookDao.getById(id);
        } else {
            bookDao.update(book);
        }
        return bookFormatter.format(book);
    }

    private Book bookBuilder(long bookId, long authorId, String name, String genresIds) {
        var author = authorDao.getById(authorId);

        List<Genre> genres = Arrays.stream(genresIds.split(","))
                .map(id -> genreDao.getById(Long.parseLong(id)))
                .collect(Collectors.toList());

        return new Book(bookId, author, name, genres);
    }

}
