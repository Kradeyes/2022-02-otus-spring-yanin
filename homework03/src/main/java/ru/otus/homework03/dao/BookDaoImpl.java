package ru.otus.homework03.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.homework03.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public void insert(String bookTitle, long bookAuthorId, long bookGenreId) {
        namedParameterJdbcOperations.update("insert into books (`booktitle`, `bookauthorid`, `bookgenreid`) values (:booktitle, :bookauthorid, :bookgenreid)",
                Map.of("booktitle", bookTitle, "bookauthorid", bookAuthorId, "bookgenreid", bookGenreId));
    }

    @Override
    public List<Book> getBookListByBookTitleAndBookAuthorIdAndBookGenreId(String bookTitle, long bookAuthorId, long bookGenreId) {
        Map<String, Object> params = Map.of("booktitle", bookTitle, "bookauthorid", bookAuthorId, "bookgenreid", bookGenreId);
        return namedParameterJdbcOperations.query(
                "select * from books where booktitle = :booktitle and bookauthorid = :bookauthorid and bookgenreid = :bookgenreid", params, new BookMapper());
    }

    @Override
    public List<Book> getAllBooks() {
        return namedParameterJdbcOperations.query("select * from books", new BookMapper());
    }

    @Override
    public void deleteBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params);
    }

    @Override
    public List<Book> findBooksByGenreId(long bookGenreId) {
        Map<String, Object> params = Collections.singletonMap("bookgenreid", bookGenreId);
        return namedParameterJdbcOperations.query(
                "select * from books where bookgenreid = :bookgenreid", params, new BookMapper());
    }

    @Override
    public List<Book> findBooksByAuthorId(long bookAuthorId) {
        Map<String, Object> params = Collections.singletonMap("bookauthorid", bookAuthorId);
        return namedParameterJdbcOperations.query(
                "select * from books where bookauthorid = :bookauthorid", params, new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String bookTitle = resultSet.getString("booktitle");
            long bookAuthorId = resultSet.getLong("bookauthorid");
            long bookGenreId = resultSet.getLong("bookgenreid");
            return new Book(id, bookTitle, bookAuthorId, bookGenreId);
        }
    }
}
