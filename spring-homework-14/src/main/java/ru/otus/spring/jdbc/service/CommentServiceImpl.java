package ru.otus.spring.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.jdbc.domain.Comment;
import ru.otus.spring.jdbc.formatter.CommentFormatter;
import ru.otus.spring.jdbc.repository.BookRepository;
import ru.otus.spring.jdbc.repository.CommentRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final CommentFormatter formatter;

    @Override
    public String createComment(String bookId, String text) {
        var book = bookRepository.findById(bookId);
        var comment = new Comment(null, book.orElseThrow(), text);
        comment = commentRepository.save(comment);
        return formatter.format(comment);
    }

    @Override
    public String getAllComments() {
        var books = commentRepository.findAll();
        return books.stream()
                .map(formatter::format)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String getCommentById(String commentId) {
        return formatter.format(commentRepository.findById(commentId).orElseThrow());
    }

    @Override
    public String deleteById(String commentId) {
        commentRepository.deleteById(commentId);
        return String.format("Comment with ID: %s was deleted", commentId);
    }

    @Override
    public String updateComment(String commentId, String bookId, String text) {
        var book = bookRepository.findById(bookId);
        var comment = new Comment(commentId, book.orElseThrow(), text);
        return formatter.format(commentRepository.save(comment));
    }
}
