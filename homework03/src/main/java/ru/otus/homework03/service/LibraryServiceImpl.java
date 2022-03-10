package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Genre;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;

    @Override
    public void createNewGenre(String genreName) {
        genreService.createNewGenre(genreName);
    }

    @Override
    public void deleteGenre(String genreName, MessageSource messageSource) {
        long genreId = genreService.getIdByGenreName(genreName);
        if (!bookService.checkTheExistenceOfABookByGenreId(genreId) && genreId != 0) {
            genreService.deleteGenre(genreId);
        } else {
            System.out.println(messageSource.getMessage("error.deleteGenre", null, Locale.getDefault()));
        }
    }

    @Override
    public void showAllGenres(MessageSource messageSource) {
        List<Genre> genreList = genreService.getAllGenres();
        if (!genreList.isEmpty()) {
            for (Genre genre : genreList) {
                System.out.println(genre);
            }
        } else {
            System.out.println(messageSource.getMessage("error.showAllGenres", null, Locale.getDefault()));
        }
    }

    @Override
    public void createNewAuthor(String authorName, String authorSurname) {
        authorService.createNewAuthor(authorName, authorSurname);
    }

    @Override
    public void deleteAuthor(String authorName, String authorSurname, MessageSource messageSource) {
        long authorId = authorService.getIdByAuthorNameAndSurname(authorName, authorSurname);
        if (!bookService.checkTheExistenceOfABookByAuthorId(authorId) && authorId != 0) {
            authorService.deleteAuthor(authorId);
        } else {
            System.out.println(messageSource.getMessage("error.deleteAuthor", null, Locale.getDefault()));
        }
    }

    @Override
    public void showAllAuthors(MessageSource messageSource) {
        List<Author> authorList = authorService.getAllAuthors();
        if (!authorList.isEmpty()) {
            for (Author author : authorList) {
                System.out.println(author);
            }
        } else {
            System.out.println(messageSource.getMessage("error.showAllAuthors", null, Locale.getDefault()));
        }
    }

    @Override
    public void createNewBook(String bookTitle, String authorName, String authorSurname, String genreName) {
        long bookAuthorId;
        long bookGenreId;
        createNewAuthor(authorName, authorSurname);
        bookAuthorId = authorService.getIdByAuthorNameAndSurname(authorName, authorSurname);
        createNewGenre(genreName);
        bookGenreId = genreService.getIdByGenreName(genreName);
        bookService.createNewBook(bookTitle, bookAuthorId, bookGenreId);
    }

    @Override
    public void deleteBook(String bookTitle, String authorName, String authorSurname, String genreName, MessageSource messageSource) {
        long bookAuthorId = authorService.getIdByAuthorNameAndSurname(authorName, authorSurname);
        long bookGenreId = genreService.getIdByGenreName(genreName);
        long bookId = bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId(bookTitle, bookAuthorId, bookGenreId);
        if (bookId != 0) {
            bookService.deleteBook(bookId);
        } else {
            System.out.println(messageSource.getMessage("error.deleteBook", null, Locale.getDefault()));
        }
    }

    @Override
    public void showAllBooks(MessageSource messageSource) {
        List<Book> bookList = bookService.getAllBooks();
        if (!bookList.isEmpty()) {
            for (Book book : bookList) {
                System.out.println(book);
            }
        } else {
            System.out.println(messageSource.getMessage("error.showAllBooks", null, Locale.getDefault()));
        }
    }
}
