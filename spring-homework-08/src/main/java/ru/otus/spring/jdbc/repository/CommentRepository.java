package ru.otus.spring.jdbc.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.jdbc.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByBookId(String bookId);
}
