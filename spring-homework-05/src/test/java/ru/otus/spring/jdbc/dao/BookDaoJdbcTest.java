package ru.otus.spring.jdbc.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@JdbcTest
@ComponentScan(value = "ru.otus.spring.jdbc.dao")
class BookDaoJdbcTest {

    @Autowired
    private BookDao dao;

    private Author author;

    @BeforeEach
    void init() {
        author = new Author(1, "Jack", "London", Collections.emptyList());
    }

    @Test
    void count() {
        assertThat(dao.count()).isEqualTo(2);
    }

    @Test
    void insert() {
        var newBook = new Book(0, author, "test book", Collections.emptyList());
        assertThat(dao.insert(newBook)).isNotZero().isGreaterThan(0);
    }

    @Test
    void update() {
        List<Genre> genres = List.of(
                new Genre(1, "comedy"),
                new Genre(2, "drama")
        );

        var updateBook = new Book(1, author, "test book", genres);
        dao.update(updateBook);
        assertThat(dao.getById(1))
                .usingRecursiveComparison()
                .isEqualTo(updateBook);
    }

    @Test
    void deleteById() {
        dao.deleteById(1);
        assertThatCode(() -> dao.getById(1))
                .isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getById() {
        List<Genre> genres = List.of(
             new Genre(1, "comedy"),
             new Genre(2, "drama")
        );

        var expectedBook = new Book(1, author, "Мартин Иден", genres);
        assertThat(dao.getById(1))
                .usingRecursiveComparison()
                .isNotNull()
                .isEqualTo(expectedBook);
    }

    @Test
    void getAll() {

        List<Genre> genres = List.of(
                new Genre(1, "comedy"),
                new Genre(2, "drama")
        );

        var expectedBooks = List.of(
            new Book(1, author, "Мартин Иден", genres),
            new Book(2, author, "Любовь к жизни", Collections.emptyList())
        );

        assertThat(dao.getAll())
                .usingRecursiveComparison()
                .asList()
                .hasSize(2)
                .containsExactlyElementsOf(expectedBooks);
    }

    @Test
    void getBooksByGenreId() {
        List<Genre> genres = List.of(
                new Genre(1, "comedy"),
                new Genre(2, "drama")
        );

        var expectedBooks = List.of(
                new Book(1, author, "Мартин Иден", genres)
        );

        assertThat(dao.getBooksByGenreId(1))
                .usingRecursiveComparison().asList()
                .hasSize(1)
                .containsExactlyElementsOf(expectedBooks);
    }

    @Test
    void getBooksByAuthorId() {
        List<Genre> genres = List.of(
                new Genre(1, "comedy"),
                new Genre(2, "drama")
        );
        var expectedBooks = List.of(
                new Book(1, author, "Мартин Иден", genres),
                new Book(2, author, "Любовь к жизни", Collections.emptyList())
        );

        assertThat(dao.getBooksByAuthorId(1))
                .usingRecursiveComparison().asList()
                .hasSize(2)
                .containsExactlyElementsOf(expectedBooks);
    }
}