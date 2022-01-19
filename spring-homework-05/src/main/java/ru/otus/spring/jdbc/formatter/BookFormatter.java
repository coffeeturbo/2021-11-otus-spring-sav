package ru.otus.spring.jdbc.formatter;

import org.springframework.stereotype.Component;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.stream.Collectors;

@Component
public class BookFormatter {

    public String format(Book book) {


        var genres = book.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", "));

        return String.format("ID: %s, AuthorName: %s, NAME: %s, GENRES: %s",
                book.getId(),
                book.getAuthor().getFirstName() + "" + book.getAuthor().getLastName(),
                book.getName(),
                genres
        );
    }
}
