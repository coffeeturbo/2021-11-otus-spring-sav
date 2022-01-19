package ru.otus.spring.jdbc.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.exception.DataAccessException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName(" ДАО Жанра")
@DataJpaTest
@ComponentScan(value = "ru.otus.spring.jdbc.repository")
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepository dao;

    @DisplayName(" Получить количество жанров ")
    @Test
    void count() {
        assertThat(dao.count()).isEqualTo(2);
    }

    @DisplayName(" Создать жанр ")
    @Test
    void insert() throws DataAccessException {
        var newGenre = new Genre(0, "test genre");
        var id = dao.insert(newGenre);
        assertThat(id).isNotZero();
    }

    @DisplayName(" Обновить жанр ")
    @Test
    void update() {
        var updateGenre = new Genre(0, "test genre");
        dao.update(updateGenre);
    }

    @DisplayName(" Удалить жанр по id ")
    @Test
    void deleteById() {
        dao.deleteById(2);

        assertThatCode(() -> dao.getById(2))
                .isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName(" Получить жанр по id")
    @Test
    void getById() {
        var expectedGenre = new Genre(1, "comedy");
        assertThat(dao.getById(1)).isEqualTo(expectedGenre);
    }

    @DisplayName(" Получить все жанры ")
    @Test
    void getAll() {
        var expectedGenres = List.of(
            new Genre(1, "comedy"),
                new Genre(2, "drama")
        );

        assertThat(dao.getAll()).hasSize(2)
                .containsExactlyElementsOf(expectedGenres);
    }

    @DisplayName(" Получить жанры по id ")
    @Test
    void getGenresByBookId() {
        var expectedGenres = List.of(
                new Genre(1, "comedy"),
                new Genre(2, "drama")
        );

        assertThat(dao.getGenresByBookId(1)).isEqualTo(expectedGenres);
    }
}