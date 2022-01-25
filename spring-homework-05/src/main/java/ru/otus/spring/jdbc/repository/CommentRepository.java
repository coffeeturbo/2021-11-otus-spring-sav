package ru.otus.spring.jdbc.repository;

import ru.otus.spring.jdbc.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    long count();
    Comment save(Comment book);
    void deleteById(long id);
    Optional<Comment> getById(long id);
    List<Comment> getAll();
}
