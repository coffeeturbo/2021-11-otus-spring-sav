package ru.otus.spring.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.jdbc.domain.Comment;
import ru.otus.spring.jdbc.formatter.CommentFormatter;
import ru.otus.spring.jdbc.repository.BookRepository;
import ru.otus.spring.jdbc.repository.CommentRepositoryJpa;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJpa commentRepository;
    private final BookRepository bookRepository;
    private final CommentFormatter formatter;

    @Transactional
    @Override
    public String createComment(long bookId, String text) {
        var book = bookRepository.getById(bookId);
        var comment = new Comment(0, book.orElseThrow(), text);
        comment = commentRepository.save(comment);
        return formatter.format(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public String getAllComments() {
        var books = commentRepository.getAll();
        return books.stream()
                .map(formatter::format)
                .collect(Collectors.joining(", "));
    }

    @Transactional(readOnly = true)
    @Override
    public String getCommentById(long commentId) {
        return formatter.format(commentRepository.getById(commentId).orElseThrow());
    }

    @Transactional
    @Override
    public String deleteById(long commentId) {
        commentRepository.deleteById(commentId);
        return String.format("Comment with ID: %s was deleted", commentId);
    }

    @Transactional
    @Override
    public String updateComment(long commentId, long bookId, String text) {
        var book = bookRepository.getById(bookId);
        var comment = new Comment(commentId, book.orElseThrow(), text);
        return formatter.format(commentRepository.save(comment));
    }
}
