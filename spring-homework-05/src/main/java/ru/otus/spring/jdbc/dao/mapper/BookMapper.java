package ru.otus.spring.jdbc.dao.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.jdbc.dao.AuthorDao;
import ru.otus.spring.jdbc.dao.GenreDao;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper implements RowMapper<Book> {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        var author = authorDao.getById(rs.getLong("author_id"));

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
