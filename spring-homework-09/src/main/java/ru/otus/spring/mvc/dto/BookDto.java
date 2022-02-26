package ru.otus.spring.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.mvc.domain.Author;
import ru.otus.spring.mvc.domain.Book;
import ru.otus.spring.mvc.domain.Genre;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {

    private long id;
    private Author author;
    private long authorId;
    private String name;
    private List<Genre> genres;
    private long[] bookGenreIds;


    public static BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getAuthor(),
                book.getAuthor().getId(),
                book.getName(),
                book.getGenres(),
                book.getGenres().stream().mapToLong(Genre::getId).toArray()
        );
    }

    public Book toDomainObject() {
        return new Book(
                id,
                author,
                name,
                genres
        );
    }
}
