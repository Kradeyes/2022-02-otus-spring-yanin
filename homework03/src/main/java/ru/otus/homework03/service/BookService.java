package ru.otus.homework03.service;

import ru.otus.homework03.domain.Book;

import java.util.List;

public interface BookService {
    long getIdByBookTitleAndBookAuthorIdAndBookGenreId(String bookTitle, long bookAuthorId, long bookGenreId);

    void createNewBook(String bookTitle, long bookAuthorId, long bookGenreId);

    void deleteBook(long bookId);

    boolean checkTheExistenceOfABookByGenreId(long bookGenreId);

    boolean checkTheExistenceOfABookByAuthorId(long bookAuthorId);

    List<Book> getAllBooks();
}
