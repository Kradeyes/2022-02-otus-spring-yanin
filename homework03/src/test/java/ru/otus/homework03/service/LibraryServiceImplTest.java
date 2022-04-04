package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.generator.AuthorGenerator;
import ru.otus.homework03.generator.BookGenerator;
import ru.otus.homework03.generator.CommentaryGenerator;
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
    private CommentaryService commentaryService;
    @Mock
    private MessageSource messageSource;

    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryServiceImpl(authorService, bookService, genreService, commentaryService);
    }

    @Test
    @DisplayName("создать новый жанр")
    void createNewGenreTest() {
        Genre genre = GenreGenerator.generateGenre();
        libraryService.createNewGenre(genre);
        verify(genreService, times(1)).createNewGenre(genre);
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
        Author author = AuthorGenerator.generateAuthor();
        libraryService.createNewAuthor(author);
        verify(authorService, times(1)).createNewAuthor(author);
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
        Book book = BookGenerator.generateBook();
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        libraryService.createNewBook(book);
        verify(bookService, times(1)).createNewBook(book, Boolean.TRUE);
    }

    @Test
    @DisplayName("удалить книгу")
    void deleteBookTest() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1L, 1L)).thenReturn(1L);
        libraryService.deleteBook("someTitle", "Ivan", "Ivanov", "Horror", messageSource);
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    @DisplayName("не удалять книгу, потому что её не существует")
    void notDeleteBookBecauseBookNotExistTest() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1L, 1L)).thenReturn(0L);
        libraryService.deleteBook("someTitle", "Ivan", "Ivanov", "Horror", messageSource);
        verify(bookService, times(0)).deleteBook(0L);
    }

    @Test
    @DisplayName("вывести список всех книг")
    void showAllBooksTest() {
        when(bookService.getAllBooks()).thenReturn(BookGenerator.generateBooksList());
        libraryService.showAllBooks(messageSource);
    }

    @Test
    @DisplayName("не выводить список книг, потому что их не существует")
    void notShowAllBooksTest() {
        when(bookService.getAllBooks()).thenReturn(new ArrayList<>());
        libraryService.showAllBooks(messageSource);
    }

    @Test
    @DisplayName("создать новый комментарий")
    void createNewCommentaryTest() {
        Commentary finalCommentary = CommentaryGenerator.generateCommentary();
        finalCommentary.setBook(BookGenerator.generateBookWithIdForAll());
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1L, 1L)).thenReturn(1L);
        libraryService.createNewCommentary(CommentaryGenerator.generateCommentary(), BookGenerator.generateBook(), messageSource);
        verify(commentaryService, times(1)).createNewCommentary(finalCommentary);
    }

    @Test
    @DisplayName("не создавать новый комментарий, потому что книги не существует")
    void notCreateNewCommentaryTest() {
        Commentary finalCommentary = CommentaryGenerator.generateCommentary();
        finalCommentary.setBook(BookGenerator.generateBookWithIdForAll());
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1L, 1L)).thenReturn(0L);
        libraryService.createNewCommentary(CommentaryGenerator.generateCommentary(), BookGenerator.generateBook(), messageSource);
        verify(commentaryService, times(0)).createNewCommentary(finalCommentary);
    }

    @Test
    @DisplayName("удалить комментарий")
    void deleteCommentaryTest() {
        when(commentaryService.getIdByCommentaryName(any())).thenReturn(1L);
        libraryService.deleteCommentary("good comment", messageSource);
        verify(commentaryService, times(1)).deleteCommentary(1L);
    }

    @Test
    @DisplayName("не удалять комментарий, потому что его не существует")
    void dontDeleteCommentaryBecauseCommentaryNotExistTest() {
        when(commentaryService.getIdByCommentaryName(any())).thenReturn(0L);
        libraryService.deleteCommentary("good comment", messageSource);
        verify(commentaryService, times(0)).deleteCommentary(0L);
    }

    @Test
    @DisplayName("вывести список всех комментариев для книги")
    void showAllCommentaryForBookTest() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1L, 1L)).thenReturn(1L);
        when(commentaryService.getAllCommentariesByBookId(1L)).thenReturn(CommentaryGenerator.generateCommentaryList());
        libraryService.showAllCommentariesByBookId(BookGenerator.generateBook(), messageSource);
    }

    @Test
    @DisplayName("не выводить список комментариев, потому что их не существует")
    void notShowAllCommentaryForBookTest() {
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        when(bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId("someTitle", 1L, 1L)).thenReturn(1L);
        when(commentaryService.getAllCommentariesByBookId(1L)).thenReturn(new ArrayList<>());
        libraryService.showAllCommentariesByBookId(BookGenerator.generateBook(), messageSource);
    }
}
