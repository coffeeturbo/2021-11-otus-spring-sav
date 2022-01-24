package ru.otus.spring.jdbc.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.otus.spring.jdbc.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(" Репозиторий Автора ")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
@ComponentScan(value = "ru.otus.spring.jdbc.repository")
class AuthorRepositoryJpaTest {

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
    void insert() {
        var newAuthor = new Author(0, "Jack", "Sparrow");
        authorRepository.save(newAuthor);

        assertThat(newAuthor.getId()).isGreaterThan(0);
        var actualAuthor = em.find(Author.class, newAuthor.getId());
        Assertions.assertThat(newAuthor).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(actualAuthor);
    }

    @DisplayName(" Изменить автора ")
    @Test
    void update() {
        var updatedAuthor = new Author(1, "Jack", "Sparrow");
        authorRepository.save(updatedAuthor);

        var actualAuthor = em.find(Author.class, 1L);

        assertThat(updatedAuthor)
                .usingRecursiveComparison()
                .isEqualTo(actualAuthor);
    }

    @DisplayName(" Удалить автора по id ")
    @Test
    void deleteByIdSuccess() {
        authorRepository.deleteById(1);
        var deletedAuthor = em.find(Author.class, 1L);
        assertThat(deletedAuthor).isNull();
    }

    @DisplayName(" Получить по id автора")
    @Test
    void getById() {
        var author = authorRepository.getById(1);
        var expectAuthor = em.find(Author.class, 1L);
        Assertions.assertThat(author)
                .isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(expectAuthor);
    }

    @DisplayName(" Получить всех авторов ")
    @Test
    void getAll() {
        var actualAuthors = authorRepository.getAll();
        assertThat(actualAuthors).isNotNull().hasSize(3)
                .allMatch(author -> !author.getLastName().equals(""))
                .allMatch(author -> !author.getFirstName().equals(""))
                .allMatch(author -> author.getId() != 0);
    }
}