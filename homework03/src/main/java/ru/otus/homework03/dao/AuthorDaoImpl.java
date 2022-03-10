package ru.otus.homework03.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework03.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(String name, String surname) {
        namedParameterJdbcOperations.update("insert into authors (`name`, `surname`) values (:name, :surname)",
                Map.of("name", name, "surname", surname));
    }

    @Override
    public List<Author> getAuthorListByAuthorNameAndAuthorSurname(String name, String surname) {
        Map<String, Object> params = Map.of("name", name, "surname", surname);
        return namedParameterJdbcOperations.query(
                "select * from authors where name = :name and surname = :surname", params, new AuthorMapper());
    }

    @Override
    public List<Author> getAllAuthors() {
        return namedParameterJdbcOperations.query("select * from authors", new AuthorMapper());
    }

    @Override
    public void deleteAuthorById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where id = :id", params);
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            return new Author(id, name, surname);
        }
    }
}
