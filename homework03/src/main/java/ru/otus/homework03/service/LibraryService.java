package ru.otus.homework03.service;

import org.springframework.context.MessageSource;

public interface LibraryService {
    void createNewGenre(String genreName);

    void deleteGenre(String genreName, MessageSource messageSource);

    void showAllGenres(MessageSource messageSource);

    void createNewAuthor(String authorName, String authorSurname);

    void deleteAuthor(String authorName, String authorSurname, MessageSource messageSource);

    void showAllAuthors(MessageSource messageSource);

    void createNewBook(String bookTitle, String authorName, String authorSurname , String genreName);

    void deleteBook(String bookTitle, String authorName, String authorSurname, String genreName, MessageSource messageSource);

    void showAllBooks(MessageSource messageSource);
}
