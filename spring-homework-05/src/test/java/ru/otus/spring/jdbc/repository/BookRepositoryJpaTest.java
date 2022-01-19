package ru.otus.spring.jdbc.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.exception.DataAccessException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName(" ДАО Книги ")
@DataJpaTest
@ComponentScan(value = "ru.otus.spring.jdbc.repository")
class BookRepositoryJpaTest {

    @Autowired
    private BookRepository dao;

    private Author author;

    @BeforeEach
    void init() {
        author = new Author(1, "Jack", "London");
    }

    @Test
    void count() {
        assertThat(dao.count()).isEqualTo(5);
    }

    @DisplayName(" Создать книгу ")
    @Test
    void insert() throws DataAccessException {
        var newBook = new Book(0, author, "test book", Collections.emptyList());
        assertThat(dao.insert(newBook)).isNotZero().isGreaterThan(0);
    }

    @DisplayName(" Изменить книгу ")
    @Test
    void update() {
        List<Genre> genres = List.of(
                new Genre(1, "comedy")
        );

        var updateBook = new Book(1, author, "test book", genres);
        dao.update(updateBook);
        assertThat(dao.getById(1))
                .usingRecursiveComparison()
                .isEqualTo(updateBook);
    }

    @DisplayName(" Удалить книгу по id ")
    @Test
    void deleteById() {
        dao.deleteById(1);
        assertThatCode(() -> dao.getById(1))
                .isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName(" Получить книгу по id ")
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

    @DisplayName(" Получить все книги ")
    @Test
    void getAll() {

        List<Genre> genres = List.of(
                new Genre(1, "comedy"),
                new Genre(2, "drama")
        );



        assertThat(dao.getAll())
                .usingRecursiveComparison()
                .asList()
                .hasSize(5)
                .contains(new Book(1, author, "Мартин Иден", genres))
                .contains(new Book(2, author, "Любовь к жизни", Collections.emptyList()));
    }

    @DisplayName(" Получить книги по id жанра ")
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
                .isEqualTo(expectedBooks);
    }

    @DisplayName(" Получить книги по id автора ")
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