package ru.otus.spring.jdbc.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jdbc.dao.mapper.GenreMapper;
import ru.otus.spring.jdbc.domain.Genre;
import ru.otus.spring.jdbc.exception.DataAccessException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Slf4j
@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;
    private final GenreMapper genreMapper;

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM genre",
                Collections.emptyMap(), Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public long insert(Genre genre) throws DataAccessException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());

        KeyHolder generatedKey = new GeneratedKeyHolder();
        jdbc.update(
                "INSERT INTO genre (name) VALUES(:name)",
                params, generatedKey);

        return Optional.ofNullable(generatedKey.getKey())
                .orElseThrow(() -> new DataAccessException("can't save new author"))
                .longValue();
    }

    @Override
    public void update(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", genre.getId());
        params.addValue("name", genre.getName());

        jdbc.update(
                "UPDATE genre SET name = :name WHERE id=:id",
                params);
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        jdbc.update(
                "DELETE genre WHERE id = :id",
                params);
    }

    @Override
    public Genre getById(long id) {
        log.info("GenreDao getGenreById request");
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);

        return jdbc.queryForObject(
                "SELECT id, name FROM genre"
                        + " WHERE id=:id",
                params,
                genreMapper
        );
    }

    @Override
    public List<Genre> getAll() {
        log.info("GenreDao getAllGenres request");
        return jdbc.query(
                "SELECT id, name FROM genre",
                genreMapper);
    }

    @Override
    public List<Genre> getGenresByBookId(long bookId) {
        log.info("GenreDao getGenresByBookId request");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("book_id", bookId);

        return jdbc.query(
                "SELECT id, name"
                        + " FROM genre"
                        + " JOIN book_genre ON genre.id = book_genre.genre_id"
                        + " WHERE book_genre.book_id = :book_id",
                params,
                genreMapper);
    }
}
