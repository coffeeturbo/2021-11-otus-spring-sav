package ru.otus.spring.jdbc.repository.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.repository.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper implements RowMapper<Book> {
    private final GenreRepository genreRepository;

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        var author = new Author(
                rs.getLong("author_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );

        var bookId = rs.getLong("id");
        List<Genre> genres = genreRepository.getGenresByBookId(bookId);

        return new Book(
                bookId,
                author,
                rs.getString("name"),
                genres
        );
    }
}
