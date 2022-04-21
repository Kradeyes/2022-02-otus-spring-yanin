package ru.otus.homework03.shell;

import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.service.LibraryService;
import ru.otus.homework03.service.OutputService;
import ru.otus.homework03.service.ScannerService;


@ShellComponent
public class ShellStarter {
    private final LibraryService service;
    private final MessageSource messageSource;
    private final ScannerService scannerService;
    private final OutputService outputService;

    public ShellStarter(LibraryService service, MessageSource messageSource, ScannerService scannerService, OutputService outputService) {
        this.service = service;
        this.messageSource = messageSource;
        this.scannerService = scannerService;
        this.outputService = outputService;
    }

    @ShellMethod(key = {"createNewGenre", "cng"}, value = "Сommand to create new genre")
    public void createNewGenre() {
        Genre genre = new Genre();
        outputService.genreNameOutput();
        genre.setGenreName(scannerService.userInput());
        service.createNewGenre(genre);
    }

    @ShellMethod(key = {"deleteGenre", "dg"}, value = "Сommand delete genre")
    public void deleteGenre() {
        outputService.genreNameOutput();
        String genreName = scannerService.userInput();
        service.deleteGenre(genreName, messageSource);
    }

    @ShellMethod(key = {"showAllGenres", "sag"}, value = "Сommand to show all genres")
    public void showAllGenres() {
        outputService.allGenresOutput();
        service.showAllGenres(messageSource);
    }

    @ShellMethod(key = {"createNewAuthor", "cna"}, value = "Сommand to create new author")
    public void createNewAuthor() {
        Author author = new Author();
        outputService.authorNameOutput();
        author.setName(scannerService.userInput());
        outputService.authorSurnameOutput();
        author.setSurname(scannerService.userInput());
        service.createNewAuthor(author);
    }

    @ShellMethod(key = {"deleteAuthor", "da"}, value = "Сommand to delete author")
    public void deleteAuthor() {
        outputService.authorNameOutput();
        String authorName = scannerService.userInput();
        outputService.authorSurnameOutput();
        String authorSurname = scannerService.userInput();
        service.deleteAuthor(authorName, authorSurname, messageSource);
    }

    @ShellMethod(key = {"showAllAuthors", "saa"}, value = "Сommand to show all authors")
    public void showAllAuthors() {
        outputService.allAuthorsOutput();
        service.showAllAuthors(messageSource);
    }

    @ShellMethod(key = {"createNewBook", "cnb"}, value = "Сommand to create new book")
    public void createNewBook() {
        Book book = new Book();
        Author author = new Author();
        Genre genre = new Genre();
        outputService.bookTitleOutput();
        book.setBookTitle(scannerService.userInput());
        outputService.authorNameOutput();
        author.setName(scannerService.userInput());
        outputService.authorSurnameOutput();
        author.setSurname(scannerService.userInput());
        book.setAuthor(author);
        outputService.genreNameOutput();
        genre.setGenreName(scannerService.userInput());
        book.setGenre(genre);
        service.createNewBook(book);
    }

    @ShellMethod(key = {"deleteBook", "db"}, value = "Сommand to delete book")
    public void deleteBook() {
        outputService.bookTitleOutput();
        String bookTitle = scannerService.userInput();
        outputService.authorNameOutput();
        String authorName = scannerService.userInput();
        outputService.authorSurnameOutput();
        String authorSurname = scannerService.userInput();
        outputService.genreNameOutput();
        String genreName = scannerService.userInput();
        service.deleteBook(bookTitle, authorName, authorSurname, genreName, messageSource);
    }

    @ShellMethod(key = {"showAllBooks", "sab"}, value = "Сommand to show all books")
    public void showAllBooks() {
        outputService.allBooksOutput();
        service.showAllBooks(messageSource);
    }

    @ShellMethod(key = {"createNewCommentary", "cnc"}, value = "Сommand to create new commentary")
    public void createNewCommentary() {
        Book book = new Book();
        Author author = new Author();
        Genre genre = new Genre();
        Commentary commentary = new Commentary();
        outputService.bookTitleOutput();
        book.setBookTitle(scannerService.userInput());
        outputService.authorNameOutput();
        author.setName(scannerService.userInput());
        outputService.authorSurnameOutput();
        author.setSurname(scannerService.userInput());
        book.setAuthor(author);
        outputService.genreNameOutput();
        genre.setGenreName(scannerService.userInput());
        book.setGenre(genre);
        outputService.commentaryNameOutput();
        commentary.setName(scannerService.userInput());
        outputService.commentaryContentOutput();
        commentary.setContent(scannerService.userInput());
        service.createNewCommentary(commentary, book,messageSource);

    }

    @ShellMethod(key = {"deleteCommentary", "dc"}, value = "Сommand to delete commentary")
    public void deleteCommentary() {
        outputService.commentaryNameOutput();
        String commentaryName = scannerService.userInput();
        service.deleteCommentary(commentaryName, messageSource);
    }

    @ShellMethod(key = {"showAllCommentariesForBook", "sacfb"}, value = "Сommand to show all commentaries for book")
    public void showAllCommentariesForBook() {
        Book book = new Book();
        Author author = new Author();
        Genre genre = new Genre();
        outputService.bookTitleOutput();
        book.setBookTitle(scannerService.userInput());
        outputService.authorNameOutput();
        author.setName(scannerService.userInput());
        outputService.authorSurnameOutput();
        author.setSurname(scannerService.userInput());
        book.setAuthor(author);
        outputService.genreNameOutput();
        genre.setGenreName(scannerService.userInput());
        book.setGenre(genre);
        outputService.allCommentariesOutput();
        service.showAllCommentariesByBookId(book,messageSource);
    }
}
