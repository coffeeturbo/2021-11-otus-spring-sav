package ru.otus.spring.jdbc.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jdbc.dao.ext.BookResultSetExtractor;
import ru.otus.spring.jdbc.dao.mapper.BookMapper;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.exception.DataAccessException;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final BookMapper bookMapper;
    private final BookResultSetExtractor bookResultSetExtractor;


    @Override
    public int count() {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM book",
                Collections.emptyMap(), Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public long insert(Book book) throws DataAccessException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());

        KeyHolder generatedKey = new GeneratedKeyHolder();
        jdbc.update(
                "INSERT INTO book"
                        + " (name, author_id) VALUES(:name, :author_id)",
                params, generatedKey);

        var id = Optional.ofNullable(generatedKey.getKey())
                .orElseThrow(() -> new DataAccessException("can't save new author"))
                .longValue();

        if (id != 0) {
            insertBookGenres(id, book.getGenres());
        }

        return id;
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());

        jdbc.update(
                "UPDATE book"
                        + " SET name = :name, author_id = :author_id WHERE id=:id",
                params);

        jdbc.update(
                "DELETE FROM book_genre"
                        + " WHERE book_id=:id",
                params);

        insertBookGenres(book.getId(), book.getGenres());
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        jdbc.update(
                "DELETE book WHERE id = :id",
                params);
    }

    @Override
    public Book getById(long id) {
        log.info("BookDao getBookById request");
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(
                "SELECT b.id, b.name, b.author_id, a.first_name first_name, a.last_name last_name "
                        + " FROM book b"
                        + " LEFT JOIN author a ON b.author_id = a.id"
                        + " WHERE b.id=:id",
                params,
                bookMapper);
    }

    @Override
    public List<Book> getAll() {
        log.info("BookDao getAllBooks request");
        var result = jdbc.query(
                "SELECT b.id, b.name, a.first_name first_name, a.last_name last_name, author_id, g.id genre_id, g.name genre_name "
                        + " FROM book b"
                        + " LEFT JOIN author a ON b.author_id = a.id"
                        + " LEFT JOIN book_genre ON b.id = book_genre.book_id "
                        + " LEFT JOIN genre g ON book_genre.genre_id = g.id",
                bookResultSetExtractor);

        return new ArrayList<>(result.values());
    }

    @Override
    public List<Book> getBooksByGenreId(long genreId) {
        log.info("BookDao getBookByGenreId request");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genre_id", genreId);

        var result = jdbc.query(
        "SELECT b.id, b.name, b.author_id, a.first_name first_name, a.last_name last_name, g.id genre_id, g.name genre_name "
            + "FROM book b "
            + "LEFT JOIN author a ON b.author_id = a.id "
            + "JOIN book_genre ON b.id = book_genre.book_id "
            + "JOIN genre g ON book_genre.genre_id = g.id  "
            + "WHERE b.id in (SELECT bg.book_id FROM book_genre bg WHERE bg.genre_id = :genre_id)",
            params,
            bookResultSetExtractor
        );

        return new ArrayList<>(result.values());
    }

    @Override
    public List<Book> getBooksByAuthorId(long authorId) {
        log.info("BookDao getBooksByAuthorId request");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("author_id", authorId);

        var result = jdbc.query(
                "SELECT b.id, b.name, b.author_id author_id, a.first_name first_name, a.last_name last_name, g.id genre_id, g.name genre_name "
                        + " FROM book b"
                        + " LEFT JOIN author a ON b.author_id = a.id "
                        + " LEFT JOIN book_genre ON b.id = book_genre.book_id "
                        + " LEFT JOIN genre g ON book_genre.genre_id = g.id"
                        + " WHERE b.author_id = :author_id",
                params,
                bookResultSetExtractor);

        return new ArrayList<>(result.values());
    }

    private void insertBookGenres(long bookId, List<Genre> genres) {
        genres.forEach(genre -> {
            if (genre.getId() != 0) {
                MapSqlParameterSource params = new MapSqlParameterSource();
                params.addValue("book_id", bookId);
                params.addValue("genre_id", genre.getId());

                jdbc.update(
                        "INSERT INTO book_genre"
                                + " (book_id, genre_id) VALUES(:book_id, :genre_id)",
                        params);
            }
        });
    }
}
