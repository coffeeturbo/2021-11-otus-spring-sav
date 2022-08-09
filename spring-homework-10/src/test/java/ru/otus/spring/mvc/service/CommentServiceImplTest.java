package ru.otus.spring.mvc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.otus.spring.mvc.domain.Book;
import ru.otus.spring.mvc.domain.Comment;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = {
        "classpath:/db.changelog/data/2022-04-02-genre.sql",
        "classpath:/db.changelog/data/2022-04-01-author.sql",
        "classpath:/db.changelog/data/2022-04-01-book.sql",
        "classpath:/db.changelog/data/2022-04-01-comment.sql"
})
@SpringBootTest
class CommentServiceImplTest {

    private static final long COMMENT_ID = 1L;
    @Autowired
    private CommentService commentService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldCreateComment() {

        var book = entityManager.find(Book.class, 2L);

        var newComment = Comment.builder()
                .book(book)
                .text("Happy comment text")
                .build();

        var createdComment = commentService.createComment(newComment);

        var actualComment = entityManager.find(Comment.class, createdComment.getId());

        assertThat(actualComment)
                .isEqualTo(newComment)
                .matches(comment -> comment.getId() != 0)
                .matches(comment -> comment.getText().equals("Happy comment text"));
    }

    @Test
    void shouldGetAllComments() {
        assertThat(commentService.getAllComments())
                .hasSize(5);
    }

    @Test
    void shouldGetCommentById() {
        assertThat(commentService.getCommentById(COMMENT_ID))
                .isPresent()
                .get()
                .matches(comment -> comment.getId() != 0)
                .matches(comment -> !comment.getText().equals(""));
    }

    @Test
    void shouldDeleteById() {
        commentService.deleteById(COMMENT_ID);
        var actualComment = entityManager.find(Comment.class, COMMENT_ID);
        assertThat(actualComment).isNull();
    }

    @Test
    void shouldUpdateComment() {

        var actualComment = entityManager.find(Comment.class, COMMENT_ID);

        var updatedCommentText = Comment.builder()
                .id(COMMENT_ID)
                .text("updated text")
                .book(actualComment.getBook())
                .build();

        commentService.updateComment(updatedCommentText);

        assertThat(entityManager.find(Comment.class, COMMENT_ID))
                .isNotEqualTo(actualComment)
                .matches(comment -> "updated text".equals(comment.getText()));


    }
}