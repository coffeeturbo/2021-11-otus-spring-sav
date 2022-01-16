package ru.otus.spring.jdbc.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jdbc.dao.ext.BookResultSetExtractor;
import ru.otus.spring.jdbc.dao.mapper.BookMapper;
import ru.otus.spring.jdbc.domain.Book;
import ru.otus.spring.jdbc.domain.Genre;

import java.util.*;

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
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());

        KeyHolder generatedKey = new GeneratedKeyHolder();
        jdbc.update(
                "INSERT INTO book"
                        + " (name, author_id) VALUES(:name, :author_id)",
                params, generatedKey);

        var id = Optional.ofNullable(generatedKey.getKey())
                .orElse(0L)
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
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(
                "SELECT id, name, author_id FROM book"
                        + " WHERE id=:id",
                params,
                bookMapper);
    }

    @Override
    public List<Book> getAll() {
        var result = jdbc.query(
                "SELECT b.id, b.name, author_id, g.id genre_id, g.name genre_name "
                        + " FROM book b"
                        + " LEFT JOIN book_genre ON b.id = book_genre.book_id "
                        + " LEFT JOIN genre g ON book_genre.genre_id = g.id",
                bookResultSetExtractor);

        return new ArrayList<>(result.values());
    }

    @Override
    public List<Book> getBooksByGenreId(long genreId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genre_id", genreId);

        return jdbc.query(
                "SELECT id, name, author_id"
                        + " FROM book"
                        + " JOIN book_genre ON book.id = book_genre.book_id"
                        + " WHERE book_genre.genre_id = :genre_id",
                params,
                bookMapper);
    }

    @Override
    public List<Book> getBooksByAuthorId(long authorId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("author_id", authorId);

        return jdbc.query(
                "SELECT b.id, b.name, b.author_id"
                        + " FROM book b"
                        + " WHERE b.author_id = :author_id",
                params,
                bookMapper);
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
