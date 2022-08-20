package ru.otus.spring.mvc.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.mvc.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(" Репозиторий Автора ")
@Sql(value = "classpath:/db.changelog/data/2022-04-01-author.sql")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" Получить количество записей ")
    @Test
    void count() {
        var expectedCount = 3;
        assertThat(authorRepository.count()).isEqualTo(expectedCount);
    }

    @DisplayName(" Добавить автора ")
    @Test
    void shouldInsert() {
        var newAuthor = new Author(0, "Jack", "Sparrow");
        authorRepository.save(newAuthor);

        assertThat(newAuthor.getId()).isNotZero();
        var actualAuthor = em.find(Author.class, newAuthor.getId());
        Assertions.assertThat(newAuthor).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(actualAuthor);
    }

    @DisplayName(" Изменить автора ")
    @Test
    void shouldUpdate() {
        var updatedAuthor = new Author(1L, "Jack", "Sparrow");
        authorRepository.save(updatedAuthor);

        var actualAuthors = authorRepository.findAll();
        var actualAuthor = em.find(Author.class, 1L);

        assertThat(updatedAuthor)
                .usingRecursiveComparison()
                .isEqualTo(actualAuthor);
    }

    @DisplayName(" Удалить автора по id ")
    @Test
    void shouldDeleteByIdSuccess() {
        authorRepository.deleteById(1);
        var deletedAuthor = em.find(Author.class, 1L);
        assertThat(deletedAuthor).isNull();
    }

    @DisplayName(" Получить по id автора")
    @Test
    void shouldGetById() {
        var author = authorRepository.getById(1);
        var expectAuthor = em.find(Author.class, 1L);
        Assertions.assertThat(author)
                .isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(expectAuthor);

        Assertions.assertThat(author)
                .isPresent()
                .get()
                .matches(author1 -> author1.getFirstName().equals("Jack2"));
    }

    @DisplayName(" Получить всех авторов ")
    @Test
    void shouldGetAll() {
        var actualAuthors = authorRepository.findAll();
        assertThat(actualAuthors).isNotNull().hasSize(3)
                .allMatch(author -> !author.getLastName().equals(""))
                .allMatch(author -> !author.getFirstName().equals(""))
                .allMatch(author -> author.getId() != 0);
    }
}