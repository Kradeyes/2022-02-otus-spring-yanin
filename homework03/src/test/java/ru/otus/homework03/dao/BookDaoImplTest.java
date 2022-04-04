package ru.otus.homework03.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.generator.BookGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BookDaoImpl.class)
@DisplayName("Dao для работы с книгами должно: ")
class BookDaoImplTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("добавлять книгу в БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void insertTest() {
        Book expectedBook = BookGenerator.generateBook();
        bookDao.insert(expectedBook);
        Book actualBook = em.find(Book.class, 2L);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    @DisplayName("найти список книг по названию, ID автора и ID жанра")
    void getBookListByBookTitleAndBookAuthorIdAndBookGenreIdTest() {
        List<Book> expectedBooksList = BookGenerator.generateBooksList();
        List<Book> actualBooksList = bookDao.getBookListByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1);
        assertEquals(expectedBooksList, actualBooksList);
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг")
    void getAllBooksTest() {
        List<Book> actualBooksList = bookDao.getAllBooks();
        Book expectedBook = BookGenerator.generateBookWithIdForAll();
        assertThat(actualBooksList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

    @Test
    @DisplayName("удалить книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteBookByIdTest() {
        bookDao.deleteBookById(1);
        assertEquals(new ArrayList<>(), bookDao.getAllBooks());
    }

    @Test
    @DisplayName("найти книгу по ID жанра")
    void findBooksByGenreIdTest() {
        List<Book> actualBooksList = bookDao.findBooksByGenreId(1);
        Book expectedBook = BookGenerator.generateBookWithIdForAll();
        assertThat(actualBooksList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

    @Test
    @DisplayName("найти книгу по ID автора")
    void findBooksByAuthorIdTest() {
        List<Book> actualBooksList = bookDao.findBooksByAuthorId(1);
        Book expectedBook = BookGenerator.generateBookWithIdForAll();
        assertThat(actualBooksList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }
}