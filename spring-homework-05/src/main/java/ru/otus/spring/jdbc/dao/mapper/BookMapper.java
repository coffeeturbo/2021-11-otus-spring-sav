package ru.otus.spring.jdbc.dao.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.jdbc.dao.GenreDao;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper implements RowMapper<Book> {
    private final GenreDao genreDao;

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        var author = new Author(
                rs.getLong("author_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );

        var bookId = rs.getLong("id");
        List<Genre> genres = genreDao.getGenresByBookId(bookId);

        return new Book(
                bookId,
                author,
                rs.getString("name"),
                genres
        );
    }
}
