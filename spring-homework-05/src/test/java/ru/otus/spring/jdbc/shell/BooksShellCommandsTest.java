package ru.otus.spring.jdbc.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DisplayName(" Книги Shell команды ")
@SpringBootTest
class BooksShellCommandsTest {

    @Autowired
    private Shell shell;

    @DisplayName(" Получает все книги ")
    @Test
    void getBooks() {
        var res = (String) shell.evaluate(() -> "lb");
        assertThat(res)
            .contains(List.of(
                    "ID: 1, AuthorName: JackLondon, NAME: Мартин Иден, GENRES: comedy, drama",
                    "ID: 2, AuthorName: JackLondon, NAME: Любовь к жизни, GENRES:"
            ));
    }

    @DisplayName(" Получает книгу по id ")
    @Test
    void getBookById() {
        var res = (String) shell.evaluate(() -> "gb 1");
        assertThat(res).isEqualTo("ID: 1, AuthorName: JackLondon, NAME: Мартин Иден, GENRES: comedy, drama");
    }

    @Rollback
    @DisplayName(" Создает книгу ")
    @Test
    void createBook() {
        var res = (String) shell.evaluate(() -> "cb 1 name 1,2");
        assertThat(res).contains("AuthorName: JackLondon, NAME: name, GENRES: comedy, drama");
    }

    @Rollback
    @DisplayName(" Обновляет книгу ")
    @Test
    void updateBook() {
        var res = (String) shell.evaluate(() -> "ub 1 1 UpdatedName 1,2");
        assertThat(res).isEqualTo("ID: 1, AuthorName: JackLondon, NAME: UpdatedName, GENRES: comedy, drama");
    }

    @Rollback
    @DisplayName(" Удаляет книгу ")
    @Test
    void deleteBook() {
        var res = (String) shell.evaluate(() -> "db 1");
        assertThat(res).isEqualTo("Book 1 was deleted");
    }
}