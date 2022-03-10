package ru.otus.homework03.shell;

import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework03.service.LibraryService;
import ru.otus.homework03.service.ScannerService;

import java.util.Locale;

@ShellComponent
public class ShellStarter {
    private final LibraryService service;
    private final MessageSource messageSource;
    private final ScannerService scannerService;

    public ShellStarter(LibraryService service, MessageSource messageSource, ScannerService scannerService) {
        this.service = service;
        this.messageSource = messageSource;
        this.scannerService = scannerService;
    }

    @ShellMethod(key = {"createNewGenre", "cng"}, value = "Сommand to create new genre")
    public void createNewGenre() {
        System.out.println(messageSource.getMessage("shell.genreName", null, Locale.getDefault()));
        String genreName = scannerService.userInput();
        service.createNewGenre(genreName);
    }

    @ShellMethod(key = {"deleteGenre", "dg"}, value = "Сommand delete genre")
    public void deleteGenre() {
        System.out.println(messageSource.getMessage("shell.genreName", null, Locale.getDefault()));
        String genreName = scannerService.userInput();
        service.deleteGenre(genreName, messageSource);
    }

    @ShellMethod(key = {"showAllGenres", "sag"}, value = "Сommand to show all genres")
    public void showAllGenres() {
        System.out.println(messageSource.getMessage("shell.showAllGenres", null, Locale.getDefault()));
        service.showAllGenres(messageSource);
    }

    @ShellMethod(key = {"createNewAuthor", "cna"}, value = "Сommand to create new author")
    public void createNewAuthor() {
        System.out.println(messageSource.getMessage("shell.authorName", null, Locale.getDefault()));
        String authorName = scannerService.userInput();
        messageSource.getMessage("shell.authorSurname", null, Locale.getDefault());
        String authorSurname = scannerService.userInput();
        service.createNewAuthor(authorName, authorSurname);
    }

    @ShellMethod(key = {"deleteAuthor", "da"}, value = "Сommand to delete author")
    public void deleteAuthor() {
        System.out.println(messageSource.getMessage("shell.authorName", null, Locale.getDefault()));
        String authorName = scannerService.userInput();
        System.out.println(messageSource.getMessage("shell.authorSurname", null, Locale.getDefault()));
        String authorSurname = scannerService.userInput();
        service.deleteAuthor(authorName, authorSurname, messageSource);
    }

    @ShellMethod(key = {"showAllAuthors", "saa"}, value = "Сommand to show all authors")
    public void showAllAuthors() {
        System.out.println(messageSource.getMessage("shell.showAllAuthors", null, Locale.getDefault()));
        service.showAllAuthors(messageSource);
    }

    @ShellMethod(key = {"createNewBook", "cnb"}, value = "Сommand to create new book")
    public void createNewBook() {
        System.out.println(messageSource.getMessage("shell.bookTitle", null, Locale.getDefault()));
        String bookTitle = scannerService.userInput();
        System.out.println(messageSource.getMessage("shell.authorName", null, Locale.getDefault()));
        String authorName = scannerService.userInput();
        System.out.println(messageSource.getMessage("shell.authorSurname", null, Locale.getDefault()));
        String authorSurname = scannerService.userInput();
        System.out.println(messageSource.getMessage("shell.genreName", null, Locale.getDefault()));
        String genreName = scannerService.userInput();
        service.createNewBook(bookTitle, authorName, authorSurname, genreName);
    }

    @ShellMethod(key = {"deleteBook", "db"}, value = "Сommand to delete book")
    public void deleteBook() {
        System.out.println(messageSource.getMessage("shell.bookTitle", null, Locale.getDefault()));
        String bookTitle = scannerService.userInput();
        System.out.println(messageSource.getMessage("shell.authorName", null, Locale.getDefault()));
        String authorName = scannerService.userInput();
        System.out.println(messageSource.getMessage("shell.authorSurname", null, Locale.getDefault()));
        String authorSurname = scannerService.userInput();
        System.out.println(messageSource.getMessage("shell.genreName", null, Locale.getDefault()));
        String genreName = scannerService.userInput();
        service.deleteBook(bookTitle, authorName, authorSurname, genreName, messageSource);
    }

    @ShellMethod(key = {"showAllBooks", "sab"}, value = "Сommand to show all books")
    public void showAllBooks() {
        System.out.println(messageSource.getMessage("shell.showAllBooks", null, Locale.getDefault()));
        service.showAllBooks(messageSource);
    }
}
