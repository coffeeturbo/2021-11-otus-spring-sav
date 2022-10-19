package ru.otus.spring.jdbc.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Репозиторий Книг")
@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private MongoTemplate template;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("Получает количество комментариев")
    @Test
    void testCount() {
        long result = commentRepository.count();
        assertThat(result)
                .isPositive()
                .isEqualTo(6);
    }

    @DisplayName("Создает новый комментарий")
    @Test
    void testInsert() {
        var book = template.findById("1", Book.class);
        var result = commentRepository.save(new Comment(null, book, "text"));
        var expected = template.findById(result.getId(), Comment.class);
        assertThat(result).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("Изменяет комментарий")
    @Test
    void testUpdate() {
        var updated = commentRepository.findById("1").orElseThrow();
        updated.setText("updated text");
        var result = commentRepository.save(updated);
        var expected = template.findById(result.getId(), Comment.class);
        assertThat(result).isNotNull().isEqualTo(expected);
    }

    @DisplayName("Удаляет комментарий по id")
    @Test
    void testDeleteById() {
        var deleteId = "2";
        commentRepository.deleteById(deleteId);
        assertThat(template.findById(deleteId, Comment.class)).isNull();
    }

    @DisplayName("Получает комментарий по id")
    @Test
    void testGetById() {
        var id = "1";
        var expected = template.findById(id, Comment.class);
        var result = commentRepository.findById(id);
        assertThat(result).isPresent().get()
                .isEqualTo(expected);
    }

    @DisplayName(" Получает все комментарии ")
    @Test
    void testGetAll() {
        var result = commentRepository.findAll();
        assertThat(result).isNotNull()
                .hasSizeGreaterThan(0)
                .hasSize(5)
                .allMatch(comment -> !comment.getId().equals(""))
                .allMatch(comment -> !comment.getText().equals(""))
                .allMatch(comment -> !comment.getBook().getName().equals(""));
    }
}