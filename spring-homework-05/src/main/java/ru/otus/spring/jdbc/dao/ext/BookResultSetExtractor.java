package ru.otus.spring.jdbc.dao.ext;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.spring.jdbc.dao.AuthorDao;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BookResultSetExtractor implements ResultSetExtractor<Map<Long, Book>> {

    private final AuthorDao authorDao;

    @Override
    public Map<Long, Book> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<Long, Book> books = new HashMap<>();

        while (rs.next()) {

            var id = rs.getLong("id");
            var book = books.get(id);

            if (book == null) {
                book = new Book(
                        id,
                        authorDao.getById(rs.getLong("author_id")),
                        rs.getString("name"),
                        new ArrayList<>()
                );

                books.put(id, book);
            }

            var genreId = rs.getLong("genre_id");
            var genreName = rs.getString("genre_name");

            if (genreId != 0) {
                var genre = new Genre(genreId, genreName);
                book.getGenres().add(genre);
            }

        }

        return books;
    }
}
