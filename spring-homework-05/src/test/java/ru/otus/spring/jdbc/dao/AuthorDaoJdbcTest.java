package ru.otus.spring.jdbc.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.exception.DataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName(" Дао Автора ")
@JdbcTest
@ComponentScan(value = "ru.otus.spring.jdbc.dao")
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao dao;

    @DisplayName(" Получить количество записей ")
    @Test
    void count() {
        var expectedCount = 2;
        assertThat(dao.count()).isEqualTo(expectedCount);
    }

    @DisplayName(" Добавить автора ")
    @Test
    void insert() throws DataAccessException {
        var newAuthor = new Author(0, "Jack", "Sparrow");
        var id = dao.insert(newAuthor);
        Assertions.assertThat(id).isNotZero();

        var expectedAuthor = new Author(id, "Jack", "Sparrow");
        var actualAuthor = dao.getById(id);
        Assertions.assertThat(actualAuthor)
                .isEqualTo(expectedAuthor);
    }

    @DisplayName(" Изменить автора ")
    @Test
    void update() {
        var updatedAuthor = new Author(1, "Jack", "Sparrow");
        dao.update(updatedAuthor);

        assertThat(dao.getById(1)).isEqualTo(updatedAuthor);
    }

    @DisplayName(" Удалить автора по id ")
    @Test
    void deleteByIdSuccess() {
        dao.deleteById(1);
        assertThatCode(() -> dao.getById(1))
                .isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName(" Получить по id автора")
    @Test
    void getById() {
        var expectAuthor = new Author(1, "Jack", "London");
        var author = dao.getById(1);
        Assertions.assertThat(author).isEqualTo(expectAuthor);
    }

    @DisplayName(" Получить всех авторов ")
    @Test
    void getAll() throws DataAccessException {
        var expectAuthor = new Author(1, "Jack", "London");

        var newAuthor = new Author(0, "Jack", "Sparrow");
        var id = dao.insert(newAuthor);
        Assertions.assertThat(id).isNotZero();
        var expectedNewAuthor = dao.getById(id);


        assertThat(dao.getAll()).hasSize(3)
                .contains(expectAuthor, expectedNewAuthor);
    }
}