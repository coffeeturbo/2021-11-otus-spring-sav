package ru.otus.spring.jdbc.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Сервис Комментариев")
@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @DisplayName("Создает комментарий")
    @Test
    void createComment() {
        var comment = commentService.createComment(2L, "test comment");
        assertThat(comment)
                .isNotNull()
                .contains("BOOK_ID: 2, TEXT: test comment");
    }

    @DisplayName("Получает все комментарии")
    @Test
    void getAllComments() {
        var comments = commentService.getAllComments();
        assertThat(comments)
                .isNotNull()
                .contains(
        "ID: 1, BOOK_ID: 1, TEXT: Ужасная книга читал до упада, ID: 2, BOOK_ID: 1, TEXT: не читал, ID: 3, BOOK_ID: 1, TEXT: плохая книга,"
        );
    }

    @DisplayName("Получаем комментарий по id")
    @Test
    void getCommentById() {
        var comment = commentService.getCommentById(1L);
        assertThat(comment)
                .isNotNull()
                .isEqualTo("ID: 1, BOOK_ID: 1, TEXT: Ужасная книга читал до упада");
    }

    @DisplayName("Удаляет комментарий по id")
    @Test
    void deleteById() {
        var commentId = 1;
        var result = commentService.deleteById(commentId);

        assertThat(result)
                .isNotNull()
                .isEqualTo(String.format("Comment with ID: %s was deleted", commentId));
    }

    @DisplayName("Изменяет комментарий")
    @Test
    void updateComment() {
        var updatedComment = commentService.updateComment(8, 1, "updatedText");
        assertThat(updatedComment)
                .isNotNull()
                .isEqualTo("ID: 8, BOOK_ID: 1, TEXT: updatedText");
    }
}