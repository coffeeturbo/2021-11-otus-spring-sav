package ru.otus.spring.jdbc.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DisplayName(" Книги Shell команды ")
@SpringBootTest
class CommentShellCommandsTest {

    @Autowired
    private Shell shell;

    @DisplayName("Получает комментарии")
    @Test
    void listComments() {
        var res = (String) shell.evaluate(() -> "lc");
        assertThat(res)
                .contains(List.of(
                        "ID: 1, BOOK_ID: 1, TEXT: Ужасная книга читал до упада",
                        "ID: 2, BOOK_ID: 1, TEXT: не читал"
                ));
    }

    @DisplayName("Получает комментарий по id")
    @Test
    void getCommentById() {
        var res = (String) shell.evaluate(() -> "gc 1");
        assertThat(res).isEqualTo("ID: 1, BOOK_ID: 1, TEXT: Ужасная книга читал до упада");
    }

    @DisplayName("Создает комментарий")
    @Test
    void createComent() {
        var res = (String) shell.evaluate(() -> "cc 1 commentText");
        assertThat(res).isEqualTo("ID: 11, BOOK_ID: 1, TEXT: commentText");
    }

    @DisplayName("Изменяет комментарий")
    @Test
    void updateComment() {
        var res = (String) shell.evaluate(() -> "uc 2 2 commentText");
        assertThat(res).isEqualTo("ID: 2, BOOK_ID: 2, TEXT: commentText");
    }

    @DisplayName("Удаляет комментарий по id")
    @Test
    void deleteComment() {
        var res = (String) shell.evaluate(() -> "dc 3");
        assertThat(res).isEqualTo("Comment with ID: 3 was deleted");
    }
}