package ru.otus.spring.jdbc.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Сервис Комментариев")
@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @DisplayName("Создает комментарий")
    @Test
    void createComment() {
        var comment = commentService.createComment("1", "test comment");
        assertThat(comment)
                .isNotNull()
                .contains("BOOK_ID: 1, TEXT: test comment");
    }

    @DisplayName("Получает все комментарии")
    @Test
    void getAllComments() {
        var comments = commentService.getAllComments();
        assertThat(comments)
                .isNotNull()
                .contains(List.of(
                        "TEXT: Ужасная книга читал до упада",
                        "TEXT: Отличная книга читал до упада"
                ));
    }

    @DisplayName("Получаем комментарий по id")
    @Test
    void getCommentById() {
        var comment = commentService.getCommentById("1");
        assertThat(comment)
                .isNotNull()
                .contains(" TEXT: Ужасная книга читал до упада");
    }

    @DisplayName("Удаляет комментарий по id")
    @Test
    void deleteById() {
        var commentId = "1";
        var result = commentService.deleteById(commentId);

        assertThat(result)
                .isNotNull()
                .isEqualTo(String.format("Comment with ID: %s was deleted", commentId));
    }

    @DisplayName("Изменяет комментарий")
    @Test
    void updateComment() {
        var updatedComment = commentService.updateComment("3", "1", "updatedText");
        assertThat(updatedComment)
                .isNotNull()
                .isEqualTo("ID: 3, BOOK_ID: 1, TEXT: updatedText");
    }
}