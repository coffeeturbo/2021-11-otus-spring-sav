package ru.otus.spring.mvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.mvc.domain.Author;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@Sql(scripts = {
        "classpath:/db.changelog/data/2022-04-01-author.sql",
})
@SpringBootTest
class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findAuthorById() {
        var expectedAuthor = entityManager.find(Author.class, 1L);

        assertThat(authorService.findAuthorById(1L))
                .isPresent()
                .get()
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test
    void getAllAuthors() {
        assertThat(authorService.getAllAuthors())
                .hasSize(3);
    }

    @Test
    void save() {
        var updateAuthor = new Author(2, "FIrstName", "lastName");
        authorService.save(updateAuthor);
        assertThat(authorService.findAuthorById(2L))
                .isPresent()
                .get()
                .matches(author ->
                        author.getFirstName().equals("FIrstName")
                        && author.getLastName().equals("lastName")
                );
    }

    @Test
    void deleteById() {
        authorService.deleteById(1L);
        var expectedAuthor = entityManager.find(Author.class, 1L);

        assertThat(expectedAuthor)
                .isNull();
    }
}