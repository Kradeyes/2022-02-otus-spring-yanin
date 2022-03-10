package ru.otus.homework03.dao;

import ru.otus.homework03.domain.Book;

import java.util.List;

public interface BookDao {
    void insert(String bookTitle, long bookAuthorId, long bookGenreId);

    List<Book> getBookListByBookTitleAndBookAuthorIdAndBookGenreId(String bookTitle, long bookAuthorId, long bookGenreId);

    List<Book> getAllBooks();

    void deleteBookById(long id);

    List<Book> findBooksByAuthorId(long bookAuthorId);

    List<Book> findBooksByGenreId(long bookGenreId);
}
