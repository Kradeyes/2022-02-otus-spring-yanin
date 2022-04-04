package ru.otus.homework03.service;

import org.springframework.context.MessageSource;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;

public interface LibraryService {
    void createNewGenre(Genre genre);

    void deleteGenre(String genreName, MessageSource messageSource);

    void showAllGenres(MessageSource messageSource);

    void createNewAuthor(Author author);

    void deleteAuthor(String authorName, String authorSurname, MessageSource messageSource);

    void showAllAuthors(MessageSource messageSource);

    void createNewBook(Book book);

    void deleteBook(String bookTitle, String authorName, String authorSurname, String genreName, MessageSource messageSource);

    void showAllBooks(MessageSource messageSource);

    void createNewCommentary(Commentary commentary, Book book, MessageSource messageSource);

    void deleteCommentary(String name, MessageSource messageSource);

    void showAllCommentariesByBookId(Book book, MessageSource messageSource);
}
