package ru.otus.spring.jdbc.formatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookFormatterTest {

    @DisplayName(" Приводит книгу к строке ")
    @Test
    void format() {
        var formatter = new BookFormatter();
        var result = formatter.format(
            Book.builder()
                .id(1)
                .author(new Author(12, "testFirstName", "test"))
                .name("Book name")
                .genres(List.of(
                    Genre.builder().id(1).name("test1").build(),
                    Genre.builder().id(2).name("test2").build()
                ))
            .build()
        );
        assertThat(result).isEqualTo("ID: 1, AuthorName: testFirstNametest, NAME: Book name, GENRES: test1, test2");
    }
}