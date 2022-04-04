package ru.otus.homework03.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.generator.BookGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(BookDaoImpl.class)
@DisplayName("Dao для работы с книгами должно: ")
class BookDaoImplTest {

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("добавлять книгу в БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void insert() {
        Book expectedBook = new Book(2, "someNewTitle", 1, 1);
        bookDao.insert("someNewTitle", 1, 1);
        Book actualBook = bookDao.getAllBooks().get(1);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("найти список книг по названию, ID автора и ID жанра")
    void getBookListByBookTitleAndBookAuthorIdAndBookGenreId() {
        List<Book> expectedBooksList = BookGenerator.generateBooksList();
        List<Book> actualBooksList = bookDao.getBookListByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1);
        assertEquals(expectedBooksList, actualBooksList);
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг")
    void getAllBooks() {
        List<Book> actualBooksList = bookDao.getAllBooks();
        Book expectedBook = new Book(1, "someTitle", 1, 1);
        assertThat(actualBooksList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

    @Test
    @DisplayName("удалить книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteBookById() {
        bookDao.deleteBookById(1);
        assertEquals(new ArrayList<>(), bookDao.getAllBooks());
    }

    @Test
    @DisplayName("найти книгу по ID жанра")
    void findBooksByGenreId() {
        List<Book> actualBooksList = bookDao.findBooksByGenreId(1);
        Book expectedBook = new Book(1, "someTitle", 1, 1);
        assertThat(actualBooksList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

    @Test
    @DisplayName("найти книгу по ID автора")
    void findBooksByAuthorId() {
        List<Book> actualBooksList = bookDao.findBooksByAuthorId(1);
        Book expectedBook = new Book(1, "someTitle", 1, 1);
        assertThat(actualBooksList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }
}