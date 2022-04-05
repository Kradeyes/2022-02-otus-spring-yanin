package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework03.exception.ImpossibilityCreationException;
import ru.otus.homework03.repository.BookRepository;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.generator.BookGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса книг должен: ")
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("найти книгу по ID")
    void findBookByIdTest() {
        when(bookRepository.findById(any())).thenReturn(BookGenerator.generateOptionalBook());
        Optional<Book> optionalBook = bookService.findBookById(1L);
        assertEquals(Boolean.FALSE, optionalBook.isEmpty());
    }

    @Test
    @DisplayName("найти книгу по ID жанра")
    void checkTheExistenceOfABookByGenreIdAndReturnTrueTest() {
        Optional<Book> book = BookGenerator.generateOptionalBook();
        when(bookRepository.findBookByGenre_Id(1)).thenReturn(book);
        boolean bookExistence = bookService.checkTheExistenceOfABookByGenreId(1);
        assertEquals(Boolean.TRUE, bookExistence);
        verify(bookRepository, times(1)).findBookByGenre_Id(1);
    }

    @Test
    @DisplayName("не найти книгу по ID жанра")
    void checkTheExistenceOfABookByGenreIdAndReturnFalseTest() {
        when(bookRepository.findBookByGenre_Id(1)).thenReturn(Optional.empty());
        boolean bookExistence = bookService.checkTheExistenceOfABookByGenreId(1);
        assertEquals(Boolean.FALSE, bookExistence);
        verify(bookRepository, times(1)).findBookByGenre_Id(1);
    }

    @Test
    @DisplayName("найти книгу по ID автора")
    void checkTheExistenceOfABookByAuthorIdAndReturnTrueTest() {
        Optional<Book> book = BookGenerator.generateOptionalBook();
        when(bookRepository.findBookByAuthor_Id(1)).thenReturn(book);
        boolean bookExistence = bookService.checkTheExistenceOfABookByAuthorId(1);
        assertEquals(Boolean.TRUE, bookExistence);
        verify(bookRepository, times(1)).findBookByAuthor_Id(1);
    }

    @Test
    @DisplayName("не найти книгу по ID автора")
    void checkTheExistenceOfABookByAuthorIdAndReturnFalseTest() {
        when(bookRepository.findBookByAuthor_Id(1)).thenReturn(Optional.empty());
        boolean bookExistence = bookService.checkTheExistenceOfABookByAuthorId(1);
        assertEquals(Boolean.FALSE, bookExistence);
        verify(bookRepository, times(1)).findBookByAuthor_Id(1);
    }

    @Test
    @DisplayName("найти ID книги и вернуть его")
    void getIdByBookTitleAndBookAuthorIdAndBookGenreIdWithFoundIdTest() {
        Optional<Book> book = BookGenerator.generateOptionalBook();
        long expectedBookId = 1;
        when(bookRepository.findBookByBookTitleAndAuthor_IdAndGenre_Id("someTitle", 1, 1)).thenReturn(book);
        long actualBookId = bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1);
        assertEquals(expectedBookId, actualBookId);
        verify(bookRepository, times(1)).findBookByBookTitleAndAuthor_IdAndGenre_Id("someTitle", 1, 1);
    }

    @Test
    @DisplayName("не найти ID книги и вернуть 0")
    void getIdByBookTitleAndBookAuthorIdAndBookGenreIdWithNotFoundIdTest() {
        long expectedBookId = 0;
        when(bookRepository.findBookByBookTitleAndAuthor_IdAndGenre_Id("someTitle", 1, 1)).thenReturn(Optional.empty());
        long actualBookId = bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1, 1);
        assertEquals(expectedBookId, actualBookId);
        verify(bookRepository, times(1)).findBookByBookTitleAndAuthor_IdAndGenre_Id("someTitle", 1, 1);
    }

    @Test
    @DisplayName("создать новую книгу")
    void createNewBookTest() {
        Book book = BookGenerator.generateBook();
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        bookService.createNewBook(book, Boolean.FALSE);
        verify(bookRepository, times(1)).save(book);
    }


    @Test
    @DisplayName("не создать новую книгу, потому что она уже существует")
    void notCreateNewBookTest() {
        Book book = BookGenerator.generateBookWithIdForAll();
        List<Book> bookList = BookGenerator.generateBooksList();
        when(bookRepository.findAll()).thenReturn(bookList);
        assertThatThrownBy(() -> bookService.createNewBook(book, Boolean.TRUE))
                .isInstanceOf(ImpossibilityCreationException.class);
   }

    @Test
    @DisplayName("удалить книгу по ID")
    void deleteBook() {
        bookService.deleteBook(1);
        verify(bookRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("вернуть список всех книг")
    void getAllBooks() {
        List<Book> expectedBooksList = BookGenerator.generateBooksList();
        when(bookRepository.findAll()).thenReturn(expectedBooksList);
        List<Book> actualBooksList = bookService.getAllBooks();
        assertEquals(expectedBooksList, actualBooksList);
        verify(bookRepository, times(1)).findAll();
    }
}