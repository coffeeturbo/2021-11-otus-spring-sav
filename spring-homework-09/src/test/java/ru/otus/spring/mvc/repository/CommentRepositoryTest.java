package ru.otus.spring.mvc.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.mvc.domain.Book;
import ru.otus.spring.mvc.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName(" Репозиторий комментариев ")
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName(" Получает количество комментариев ")
    @Test
    void testCount() {
        long result = commentRepository.count();
        assertThat(result)
                .isGreaterThan(0)
                .isEqualTo(10);
    }

    @DisplayName(" Создает новый комментарий ")
    @Test
    void testInsert() {
        var book = em.find(Book.class, 1L);
        var result = commentRepository.save(Comment.builder()
                        .book(book)
                .text("text").build());
        var expected = em.find(Comment.class, result.getId());
        assertThat(result).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName(" Изменяет комментарий ")
    @Test
    void testUpdate() {
        var result = commentRepository.save(Comment.builder().id(1).text("updated text").build());
        var expected = em.find(Comment.class, result.getId());
        assertThat(result).isNotNull().isEqualTo(expected);
    }

    @DisplayName(" Удаляет комментарий по id")
    @Test
    void testDeleteById() {
        var deleteId = 2L;
        commentRepository.deleteById(deleteId);
        assertThat(em.find(Comment.class, deleteId)).isNull();
    }

    @DisplayName(" Получает комментарий по id")
    @Test
    void testGetById() {
        var id = 1L;
        var expected = em.find(Comment.class, id);
        var result = commentRepository.getById(id);
        assertThat(result).isPresent().get()
                        .isEqualTo(expected);
    }

    @DisplayName(" Получает все комментарии ")
    @Test
    void testGetAll() {
        var result = commentRepository.findAll();
        assertThat(result).isNotNull()
                .hasSize(10)
                .allMatch(comment -> comment.getId() != 0)
                .allMatch(comment -> !comment.getText().equals(""))
                .allMatch(comment -> !comment.getBook().equals(""));
    }
}