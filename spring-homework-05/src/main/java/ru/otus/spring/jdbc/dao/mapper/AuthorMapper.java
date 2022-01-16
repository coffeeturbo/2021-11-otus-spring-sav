package ru.otus.spring.jdbc.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.spring.jdbc.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {

        var id = rs.getLong("id");
        var firstName = rs.getString("first_name");
        var lastName = rs.getString("last_name");

        return new Author(id, firstName, lastName);
    }
}
