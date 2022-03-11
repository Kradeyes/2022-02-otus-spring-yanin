package ru.otus.homework03.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.generator.AuthorGenerator;
import ru.otus.homework03.generator.BookGenerator;
import ru.otus.homework03.generator.CommentaryGenerator;
import ru.otus.homework03.generator.GenreGenerator;
import ru.otus.homework03.service.LibraryService;
import ru.otus.homework03.service.ScannerService;

import static org.mockito.Mockito.*;

@DisplayName("Тест команд shell ")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class ShellStarterTest {
    @MockBean
    private LibraryService service;
    @MockBean
    private MessageSource messageSource;
    @MockBean
    private ScannerService scannerService;

    @Autowired
    private Shell shell;

    private static final String COMMAND_CREATE_GENRE = "cng";
    private static final String COMMAND_DELETE_GENRE = "dg";
    private static final String COMMAND_SHOW_ALL_GENRES = "sag";
    private static final String COMMAND_CREATE_AUTHOR = "cna";
    private static final String COMMAND_DELETE_AUTHOR = "da";
    private static final String COMMAND_SHOW_ALL_AUTHORS = "saa";
    private static final String COMMAND_CREATE_BOOK = "cnb";
    private static final String COMMAND_DELETE_BOOK = "db";
    private static final String COMMAND_SHOW_ALL_BOOKS = "sab";
    private static final String COMMAND_CREATE_COMMENTARY = "cnc";
    private static final String COMMAND_DELETE_COMMENTARY = "dc";
    private static final String COMMAND_SHOW_ALL_COMMENTARIES_FOR_BOOK = "sacfb";


    @DisplayName("должен запустить создание нового жанра")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void createNewGenreTest() {
        Genre genre = GenreGenerator.generateGenre();
        when(scannerService.userInput()).thenReturn(genre.getGenreName());
        shell.evaluate(() -> COMMAND_CREATE_GENRE);
        verify(service, times(1)).createNewGenre(genre);
    }

    @DisplayName("должен запустить удаление жанра")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void deleteGenreTest() {
        String genre = "Horror";
        when(scannerService.userInput()).thenReturn(genre);
        shell.evaluate(() -> COMMAND_DELETE_GENRE);
        verify(service, times(1)).deleteGenre(genre, messageSource);
    }

    @DisplayName("должен запустить вывод всех жанров")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void showAllGenresTest() {
        shell.evaluate(() -> COMMAND_SHOW_ALL_GENRES);
        verify(service, times(1)).showAllGenres(messageSource);
    }

    @DisplayName("должен запустить создание нового автора")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void createNewAuthorTest() {
        Author author = AuthorGenerator.generateAuthor();
        when(scannerService.userInput()).thenReturn(author.getName(), author.getSurname());
        shell.evaluate(() -> COMMAND_CREATE_AUTHOR);
        verify(service, times(1)).createNewAuthor(author);
    }

    @DisplayName("должен запустить удаление автора")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void deleteAuthorTest() {
        String name = "Ivan";
        String surname = "Ivanov";
        when(scannerService.userInput()).thenReturn(name, surname);
        shell.evaluate(() -> COMMAND_DELETE_AUTHOR);
        verify(service, times(1)).deleteAuthor(name, surname, messageSource);
    }

    @DisplayName("должен запустить вывод всех авторов")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void showAllAuthorsTest() {
        shell.evaluate(() -> COMMAND_SHOW_ALL_AUTHORS);
        verify(service, times(1)).showAllAuthors(messageSource);
    }

    @DisplayName("должен запустить создание новой книги")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void createNewBookTest() {
        String bookTitle = "someTitle";
        String name = "Ivan";
        String surname = "Ivanov";
        String genre = "Horror";
        when(scannerService.userInput()).thenReturn(bookTitle, name, surname, genre);
        shell.evaluate(() -> COMMAND_CREATE_BOOK);
        verify(service, times(1)).createNewBook(BookGenerator.generateBook());
    }

    @DisplayName("должен запустить удаление книги")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void deleteBookTest() {
        String bookTitle = "SomeBook";
        String name = "Ivan";
        String surname = "Ivanov";
        String genre = "Horror";
        when(scannerService.userInput()).thenReturn(bookTitle, name, surname, genre);
        shell.evaluate(() -> COMMAND_DELETE_BOOK);
        verify(service, times(1)).deleteBook(bookTitle, name, surname, genre, messageSource);
    }

    @DisplayName("должен запустить вывод всех книг")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void showAllBooksTest() {
        shell.evaluate(() -> COMMAND_SHOW_ALL_BOOKS);
        verify(service, times(1)).showAllBooks(messageSource);
    }

    @DisplayName("должен запустить создание нового комментария")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void createNewCommentaryTest() {
        Commentary commentary = CommentaryGenerator.generateCommentary();
        Book book = BookGenerator.generateBook();
        when(scannerService.userInput()).thenReturn(book.getBookTitle(), book.getAuthor().getName(),
                book.getAuthor().getSurname(), book.getGenre().getGenreName(), commentary.getName(), commentary.getContent());
        shell.evaluate(() -> COMMAND_CREATE_COMMENTARY);
        verify(service, times(1)).createNewCommentary(commentary, book, messageSource);
    }

    @DisplayName("должен запустить удаление комментария")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void deleteCommentaryTest() {
        String commentaryName = CommentaryGenerator.generateCommentary().getName();
        when(scannerService.userInput()).thenReturn(commentaryName);
        shell.evaluate(() -> COMMAND_DELETE_COMMENTARY);
        verify(service, times(1)).deleteCommentary(commentaryName, messageSource);
    }

    @DisplayName("должен запустить вывод всех комментариев для книги")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void showAllCommentariesForBookTest() {
        Book book = BookGenerator.generateBook();
        when(scannerService.userInput()).thenReturn(book.getBookTitle(), book.getAuthor().getName(), book.getAuthor().getSurname(), book.getGenre().getGenreName());
        shell.evaluate(() -> COMMAND_SHOW_ALL_COMMENTARIES_FOR_BOOK);
        verify(service, times(1)).showAllCommentariesByBookId(book, messageSource);
    }
}