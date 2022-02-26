package ru.otus.spring.jdbc.service;

public interface CommentService {
    String createComment(String bookId, String text);

    String getAllComments();

    String getCommentById(String commentId);

    String deleteById(String commentId);

    String updateComment(String commentId, String bookId, String text);
}
