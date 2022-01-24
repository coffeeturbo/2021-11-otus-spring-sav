package ru.otus.spring.jdbc.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(" Репозиторий Жанра")
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository dao;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName(" Получить количество жанров ")
    @Test
    void count() {
        assertThat(dao.count()).isEqualTo(3);
    }

    @DisplayName(" Создать жанр ")
    @Test
    void insert() {
        var newGenre = Genre.builder().name("test genre").build();
        var genre = dao.save(newGenre);
        assertThat(genre.getId()).isNotZero();
    }

    @DisplayName(" Обновить жанр ")
    @Test
    void update() {
        var updateGenre = Genre.builder().id(1).name("test genre").build();
        var genre = dao.save(updateGenre);

        var expectedGenre = entityManager.find(Genre.class, genre.getId());
        assertThat(genre).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @DisplayName(" Удалить жанр по id ")
    @Test
    void deleteById() {
        dao.deleteById(2);
        var expectedGenre = entityManager.find(Genre.class, 2L);
        assertThat(expectedGenre).isNull();
    }

    @DisplayName(" Получить жанр по id")
    @Test
    void getById() {
        var expectedGenre = entityManager.find(Genre.class, 1L);
        assertThat(dao.getById(1))
                .isPresent().get()
                .isEqualTo(expectedGenre);
    }

    @DisplayName(" Получить все жанры ")
    @Test
    void getAll() {
        assertThat(dao.findAll())
                .isNotNull()
                .hasSize(3)
                .allMatch(Objects::nonNull)
                .allMatch(genre -> genre.getId() != 0)
                .allMatch(genre -> !genre.getName().equals(""));
    }
}