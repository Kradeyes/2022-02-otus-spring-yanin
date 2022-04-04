package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework03.dao.BookDao;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.generator.BookGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса книг должен: ")
class BookServiceImplTest {
    @Mock
    private BookDao bookDao;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookDao);
    }

    @Test
    @DisplayName("найти книгу по ID жанра")
    void checkTheExistenceOfABookByGenreIdAndReturnTrueTest() {
        List<Book> bookList = BookGenerator.generateBooksList();
        when(bookDao.findBooksByGenreId(1)).thenReturn(bookList);
        boolean bookExistence = bookService.checkTheExistenceOfABookByGenreId(1);
        assertEquals(Boolean.TRUE, bookExistence);
        verify(bookDao, times(1)).findBooksByGenreId(1);
    }

    @Test
    @DisplayName("не найти книгу по ID жанра")
    void checkTheExistenceOfABookByGenreIdAndReturnFalseTest() {
        when(bookDao.findBooksByGenreId(1)).thenReturn(new ArrayList<>());
        boolean bookExistence = bookService.checkTheExistenceOfABookByGenreId(1);
        assertEquals(Boolean.FALSE, bookExistence);
        verify(bookDao, times(1)).findBooksByGenreId(1);
    }

    @Test
    @DisplayName("найти книгу по ID автора")
    void checkTheExistenceOfABookByAuthorIdAndReturnTrueTest() {
        List<Book> bookList = BookGenerator.generateBooksList();
        when(bookDao.findBooksByAuthorId(1)).thenReturn(bookList);
        boolean bookExistence = bookService.checkTheExistenceOfABookByAuthorId(1);
        assertEquals(Boolean.TRUE, bookExistence);
        verify(bookDao, times(1)).findBooksByAuthorId(1);
    }

    @Test
    @DisplayName("не найти книгу по ID автора")
    void checkTheExistenceOfABookByAuthorIdAndReturnFalseTest() {
        when(bookDao.findBooksByAuthorId(1)).thenReturn(new ArrayList<>());
        boolean bookExistence = bookService.checkTheExistenceOfABookByAuthorId(1);
        assertEquals(Boolean.FALSE, bookExistence);
        verify(bookDao, times(1)).findBooksByAuthorId(1);
    }

    @Test
    @DisplayName("найти ID книги и вернуть его")
    void getIdByBookTitleAndBookAuthorIdAndBookGenreIdWithFoundIdTest() {
        List<Book> bookList = BookGenerator.generateBooksList();
        long expectedBookId = 1;
        when(bookDao.getBookListByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1)).thenReturn(bookList);
        long actualBookId = bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1);
        assertEquals(expectedBookId, actualBookId);
        verify(bookDao, times(1)).getBookListByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1);
    }

    @Test
    @DisplayName("не найти ID книги и вернуть 0")
    void getIdByBookTitleAndBookAuthorIdAndBookGenreIdWithNotFoundIdTest() {
        long expectedBookId = 0;
        when(bookDao.getBookListByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1)).thenReturn(new ArrayList<>());
        long actualBookId = bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1);
        assertEquals(expectedBookId, actualBookId);
        verify(bookDao, times(1)).getBookListByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1);
    }

    @Test
    @DisplayName("создать новую книгу")
    void createNewBookTest() {
        Book book = BookGenerator.generateBook();
        when(bookDao.getAllBooks()).thenReturn(new ArrayList<>());
        bookService.createNewBook(book, Boolean.FALSE);
        verify(bookDao, times(1)).insert(book);
    }

    @Test
    @DisplayName("создать новую книгу, но существуюшими авторами или жанрами")
    void createNewBookWithExistingAuthorOrGenreTest() {
        Book book = BookGenerator.generateBook();
        when(bookDao.getAllBooks()).thenReturn(new ArrayList<>());
        bookService.createNewBook(book, Boolean.TRUE);
        verify(bookDao, times(1)).insertWithExistingAuthorOrGenre(book);
    }

    @Test
    @DisplayName("не создать новую книгу, потому что она уже существует")
    void notCreateNewBookTest() {
        Book book = BookGenerator.generateBookWithIdForAll();
        List<Book> bookList = BookGenerator.generateBooksList();
        when(bookDao.getAllBooks()).thenReturn(bookList);
        bookService.createNewBook(book, Boolean.TRUE);
        verify(bookDao, times(0)).insert(book);
        verify(bookDao, times(0)).insertWithExistingAuthorOrGenre(book);
    }

    @Test
    @DisplayName("удалить книгу по ID")
    void deleteBook() {
        bookService.deleteBook(1);
        verify(bookDao, times(1)).deleteBookById(1);
    }

    @Test
    @DisplayName("вернуть список всех книг")
    void getAllBooks() {
        List<Book> expectedBooksList = BookGenerator.generateBooksList();
        when(bookDao.getAllBooks()).thenReturn(expectedBooksList);
        List<Book> actualBooksList = bookService.getAllBooks();
        assertEquals(expectedBooksList, actualBooksList);
        verify(bookDao, times(1)).getAllBooks();
    }
}