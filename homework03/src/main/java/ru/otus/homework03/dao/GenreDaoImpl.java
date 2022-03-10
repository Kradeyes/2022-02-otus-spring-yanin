package ru.otus.homework03.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework03.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(String name) {
        namedParameterJdbcOperations.update("insert into genres (`name`) values (:name)",
                Map.of("name", name));
    }

    @Override
    public List<Genre> getGenreListByGenreName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        return namedParameterJdbcOperations.query(
                "select * from genres where name = :name", params, new GenreMapper());
    }

    @Override
    public List<Genre> getAllGenres() {
        return namedParameterJdbcOperations.query("select * from genres", new GenreMapper());
    }

    @Override
    public void deleteGenreById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from genres where id = :id", params);
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String genreName = resultSet.getString("name");
            return new Genre(id, genreName);
        }
    }
}
