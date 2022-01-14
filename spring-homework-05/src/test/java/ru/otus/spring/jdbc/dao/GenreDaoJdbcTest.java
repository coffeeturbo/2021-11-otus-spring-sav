package ru.otus.spring.jdbc.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@JdbcTest
@ComponentScan(value = "ru.otus.spring.jdbc.dao")
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao dao;

    @Test
    void count() {
        assertThat(dao.count()).isEqualTo(2);
    }

    @Test
    void insert() {
        var newGenre = new Genre(0, "test genre");
        var id = dao.insert(newGenre);
        assertThat(id).isNotZero();
    }

    @Test
    void update() {
        var updateGenre = new Genre(0, "test genre");
        dao.update(updateGenre);


    }

    @Test
    void deleteById() {
        dao.deleteById(2);

        assertThatCode(() -> dao.getById(2))
                .isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getById() {
        var expectedGenre = new Genre(1, "comedy");
        assertThat(dao.getById(1)).isEqualTo(expectedGenre);
    }

    @Test
    void getAll() {
        var expectedGenres = List.of(
            new Genre(1, "comedy"),
                new Genre(2, "drama")
        );

        assertThat(dao.getAll()).hasSize(2)
                .containsExactlyElementsOf(expectedGenres);
    }

    @Test
    void getGenresByBookId() {
        var expectedGenres = List.of(
                new Genre(1, "comedy"),
                new Genre(2, "drama")
        );

        assertThat(dao.getGenresByBookId(1)).isEqualTo(expectedGenres);
    }
}