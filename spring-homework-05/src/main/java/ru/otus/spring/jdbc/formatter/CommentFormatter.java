package ru.otus.spring.jdbc.formatter;

import org.springframework.stereotype.Component;
import ru.otus.spring.jdbc.domain.Comment;

@Component
public class CommentFormatter {

    public String format(Comment comment) {
        return String.format("ID: %s, BOOK_ID: %s, TEXT: %s",
                comment.getId(),
                comment.getBook().getId(),
                comment.getText());
    }
}
