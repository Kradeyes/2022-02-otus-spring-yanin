package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.homework03.generator.AuthorGenerator;
import ru.otus.homework03.generator.BookGenerator;
import ru.otus.homework03.generator.GenreGenerator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса библиотеки должен: ")
class LibraryServiceImplTest {
    @Mock
    private AuthorService authorService;
    @Mock
    private BookService bookService;
    @Mock
    private GenreService genreService;
    @Mock
    private MessageSource messageSource;

    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryServiceImpl(authorService, bookService, genreService);
    }

    @Test
    @DisplayName("создать новый жанр")
    void createNewGenreTest() {
        libraryService.createNewGenre("Horror");
        verify(genreService, times(1)).createNewGenre("Horror");
    }

    @Test
    @DisplayName("удалить жанр")
    void deleteGenreTest() {
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.checkTheExistenceOfABookByGenreId(1L)).thenReturn(false);
        libraryService.deleteGenre("Horror", messageSource);
        verify(genreService, times(1)).deleteGenre(1L);
    }

    @Test
    @DisplayName("не удалять жанр, потому что его не существует")
    void dontDeleteGenreBecauseGenreNotExistTest() {
        when(genreService.getIdByGenreName("Horror")).thenReturn(0L);
        when(bookService.checkTheExistenceOfABookByGenreId(0L)).thenReturn(false);
        libraryService.deleteGenre("Horror", messageSource);
        verify(genreService, times(0)).deleteGenre(0L);
    }

    @Test
    @DisplayName("не удалять жанр, потому что существуют книги с этим жанром")
    void dontDeleteGenreBecauseThereAreBooksWithThisGenreTest() {
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.checkTheExistenceOfABookByGenreId(1L)).thenReturn(true);
        libraryService.deleteGenre("Horror", messageSource);
        verify(genreService, times(0)).deleteGenre(1L);
    }


    @Test
    @DisplayName("вывести список всех жанров")
    void showAllGenres() {
        when(genreService.getAllGenres()).thenReturn(GenreGenerator.generateGenresList());
        libraryService.showAllGenres(messageSource);
    }

    @Test
    @DisplayName("не выводить список жанров, потому что их не существует")
    void notShowAllGenres() {
        when(genreService.getAllGenres()).thenReturn(new ArrayList<>());
        libraryService.showAllGenres(messageSource);
    }

    @Test
    @DisplayName("создать нового автора")
    void createNewAuthor() {
        libraryService.createNewAuthor("Ivan", "Ivanov");
        verify(authorService, times(1)).createNewAuthor("Ivan", "Ivanov");
    }

    @Test
    @DisplayName("удалить автора")
    void deleteAuthor() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(bookService.checkTheExistenceOfABookByAuthorId(1L)).thenReturn(false);
        libraryService.deleteAuthor("Ivan", "Ivanov", messageSource);
        verify(authorService, times(1)).deleteAuthor(1L);
    }

    @Test
    @DisplayName("не удалять автора, потому что его не существует")
    void notDeleteAuthorBecauseAuthorNotExistTest() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(0L);
        when(bookService.checkTheExistenceOfABookByAuthorId(0L)).thenReturn(false);
        libraryService.deleteAuthor("Ivan", "Ivanov", messageSource);
        verify(authorService, times(0)).deleteAuthor(0L);
    }

    @Test
    @DisplayName("не удалять автора, потому что существуют книги с этим автором")
    void notDeleteAuthorBecauseThereAreBooksWithThisAuthorTest() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(bookService.checkTheExistenceOfABookByAuthorId(1L)).thenReturn(true);
        libraryService.deleteAuthor("Ivan", "Ivanov", messageSource);
        verify(authorService, times(0)).deleteAuthor(1L);
    }

    @Test
    @DisplayName("вывести список всех авторов")
    void showAllAuthors() {
        when(authorService.getAllAuthors()).thenReturn(AuthorGenerator.generateAuthorsList());
        libraryService.showAllAuthors(messageSource);
    }

    @Test
    @DisplayName("не выводить список авторов, потому что их не существует")
    void notShowAllAuthors() {
        when(authorService.getAllAuthors()).thenReturn(new ArrayList<>());
        libraryService.showAllAuthors(messageSource);
    }

    @Test
    @DisplayName("создать новую книгу")
    void createNewBook() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan","Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        libraryService.createNewBook("someTitle", "Ivan", "Ivanov", "Horror");
        verify(genreService, times(1)).createNewGenre("Horror");
        verify(authorService, times(1)).createNewAuthor("Ivan", "Ivanov");
        verify(genreService, times(1)).createNewGenre("Horror");
        verify(bookService, times(1)).createNewBook("someTitle", 1, 1);
    }

    @Test
    @DisplayName("удалить книгу")
    void deleteBookTest() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan","Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1L, 1L)).thenReturn(1L);
        libraryService.deleteBook("someTitle","Ivan","Ivanov","Horror", messageSource);
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    @DisplayName("не удалять книгу, потому что её не существует")
    void notDeleteBookBecauseBookNotExistTest() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan","Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1L, 1L)).thenReturn(0L);
        libraryService.deleteBook("someTitle","Ivan","Ivanov","Horror", messageSource);
        verify(bookService, times(0)).deleteBook(0L);
    }

    @Test
    @DisplayName("вывести список всех книг")
    void showAllBooks() {
        when(bookService.getAllBooks()).thenReturn(BookGenerator.generateBooksList());
        libraryService.showAllBooks(messageSource);
    }

    @Test
    @DisplayName("не выводить список книг, потому что их не существует")
    void notShowAllBooks() {
        when(bookService.getAllBooks()).thenReturn(new ArrayList<>());
        libraryService.showAllBooks(messageSource);
    }
}