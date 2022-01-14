package ru.otus.spring.jdbc.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jdbc.dao.mapper.BookMapper;
import ru.otus.spring.jdbc.domain.Book;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private static final String BOOK_TABLE = "book";

    private final NamedParameterJdbcOperations jdbc;
    private final BookMapper bookMapper;


    @Override
    public int count() {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM " + BOOK_TABLE,
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
                "INSERT INTO " + BOOK_TABLE
                        + " (name, author_id) VALUES(:name, :author_id)",
                params, generatedKey);

        return Optional.of(generatedKey.getKey().longValue()).orElse(0L);
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());

        jdbc.update(
                "UPDATE "
                        + BOOK_TABLE
                        + " SET name = :name, author_id = :author_id WHERE id=:id",
                params);
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        jdbc.update(
                "DELETE " + BOOK_TABLE + " WHERE id = :id",
                params);
    }

    @Override
    public Book getById(long id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(
                "SELECT id, name, author_id FROM "
                        + BOOK_TABLE + " WHERE id=:id",
                params,
                bookMapper);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "SELECT id, name, author_id FROM " + BOOK_TABLE,
                bookMapper);
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
                        + " JOIN author_book ON b.id = author_book.book_id"
                        + " WHERE author_book.author_id = :author_id",
                params,
                bookMapper);
    }


}
