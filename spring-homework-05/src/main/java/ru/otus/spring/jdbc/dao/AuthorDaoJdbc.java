package ru.otus.spring.jdbc.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.jdbc.dao.mapper.AuthorMapper;
import ru.otus.spring.jdbc.domain.Author;
import ru.otus.spring.jdbc.exception.DataAccessException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorMapper authorMapper;

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM author",
                Collections.emptyMap(), Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public long insert(Author author) throws DataAccessException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("first_name", author.getFirstName());
        params.addValue("last_name", author.getLastName());

        KeyHolder generatedKey = new GeneratedKeyHolder();
        jdbc.update(
                "INSERT INTO author(first_name, last_name) VALUES(:first_name, :last_name)",
                params, generatedKey);

        return Optional.ofNullable(generatedKey.getKey())
                .orElseThrow(() -> new DataAccessException("can't save new author"))
                .longValue();
    }

    @Override
    public void update(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", author.getId());
        params.addValue("first_name", author.getFirstName());
        params.addValue("last_name", author.getLastName());
        jdbc.update(
                "UPDATE  author SET first_name = :first_name, last_name = :last_name WHERE id=:id",
                params);
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbc.update(
                "DELETE author WHERE id = :id",
                params);
    }

    @Override
    public Author getById(long id) {
        log.info("AuthorDao getAuthorById request");
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(
                "SELECT id, first_name, last_name FROM author"
                        + " WHERE id=:id",
                params,
                authorMapper);
    }

    @Override
    public List<Author> getAll() {
        log.info("AuthorDao getAllAuthors request");
        return jdbc.query(
                "SELECT id, first_name, last_name FROM author",
                authorMapper);
    }
}
