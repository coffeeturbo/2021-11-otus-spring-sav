package ru.otus.spring.jdbc.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.jdbc.domain.Author;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий Автора")
@DataMongoTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate template;

    @DisplayName("Ищет автора по Имени")
    @Test
    void whenFindByFirstNameSuccess() {
        var newAuthor = new Author(null, "Lermontov2", "Sparrow");
        authorRepository.save(newAuthor);

        var author = authorRepository.findOneByFirstName("Lermontov2");
        assertThat(author).isPresent().get()
                .isNotNull()
                .matches(author1 -> !author1.getId().equals(""));

        authorRepository.delete(newAuthor);
    }

    @DisplayName(" Получить количество записей ")
    @Test
    void count() {
        List<Author> authors = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            var updatedAuthor = new Author(null, "Jack " + i, "Sparrow " + i);
            authors.add(updatedAuthor);
        }

        authorRepository.saveAll(authors);
        var expectedCount = 8;
        assertThat(authorRepository.count()).isEqualTo(expectedCount);

        authorRepository.deleteAll(authors);
        assertThat(authorRepository.count()).isEqualTo(3);
    }

    @DisplayName("Добавить автора")
    @Test
    void insert() {
        var newAuthor = new Author(null, "Jack", "Sparrow");
        authorRepository.save(newAuthor);

        var saved = authorRepository.findById(newAuthor.getId());
        var templateAuthor = template.findById(saved.get().getId(), Author.class);

        assertThat(saved).isPresent().get()
                .isNotNull()
                .matches(author -> author.getFirstName().equals("Jack"))
                .matches(author -> author.getLastName().equals("Sparrow"))
                .isEqualTo(templateAuthor);

        authorRepository.delete(saved.orElseThrow());
    }

    @DisplayName("Изменить автора")
    @Test
    void update() {
        var newAuthor = new Author(null, "Jack", "Sparrow");
        authorRepository.save(newAuthor);

        newAuthor.setFirstName("Updated firstName");
        authorRepository.save(newAuthor);

        var actualAuthor = template.findById(newAuthor.getId(), Author.class);

        assertThat(newAuthor)
                .usingRecursiveComparison()
                .isEqualTo(actualAuthor);

        authorRepository.delete(newAuthor);
    }


    @DisplayName("Удалить автора по id")
    @Test
    void deleteByIdSuccess() {

        var updatedAuthor = new Author(null, "Jack", "Sparrow");
        authorRepository.save(updatedAuthor);

        authorRepository.deleteById(updatedAuthor.getId());
        var deletedAuthor = template.findById(updatedAuthor.getId(), Author.class);
        assertThat(deletedAuthor).isNull();
    }


    @DisplayName(" Получить по id автора")
    @Test
    void getById() {
        var newAuthor = new Author(null, "Jack", "Sparrow");
        authorRepository.save(newAuthor);

        var author = authorRepository.findById(newAuthor.getId());
        var expectAuthor = template.findById(newAuthor.getId(), Author.class);
        Assertions.assertThat(author)
                .isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(expectAuthor);
        authorRepository.delete(newAuthor);
    }


    @DisplayName("Получить всех авторов")
    @Test
    void getAll() {
        var actualAuthors = authorRepository.findAll();
        assertThat(actualAuthors).isNotNull().hasSize(3)
                .allMatch(author -> !author.getLastName().equals(""))
                .allMatch(author -> !author.getFirstName().equals(""))
                .allMatch(author -> !author.getId().equals(""));

    }
}