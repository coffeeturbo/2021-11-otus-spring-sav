package ru.otus.spring.jdbc.service;

public interface CommentService {
    String createComment(long bookId, String text);

    String getAllComments();

    String getCommentById(long commentId);

    String deleteById(long commentId);

    String updateComment(long commentId, long bookId, String text);
}
