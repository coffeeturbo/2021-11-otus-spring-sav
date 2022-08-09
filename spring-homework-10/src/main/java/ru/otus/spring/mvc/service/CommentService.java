package ru.otus.spring.mvc.service;

import ru.otus.spring.mvc.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Comment comment);

    List<Comment> getAllComments();

    Optional<Comment> getCommentById(long commentId);

    void deleteById(long commentId);

    Comment updateComment(Comment comment);
}
