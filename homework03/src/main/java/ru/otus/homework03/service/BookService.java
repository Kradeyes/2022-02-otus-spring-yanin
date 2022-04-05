package ru.otus.homework03.service;

import ru.otus.homework03.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> findBookById(long bookId);

    long getIdByBookTitleAndBookAuthorIdAndBookGenreId(String bookTitle, long bookAuthorId, long bookGenreId);

    Book createNewBook(Book book, boolean existingAuthorOrGenre);

    void deleteBook(long bookId);

    boolean checkTheExistenceOfABookByGenreId(long bookGenreId);

    boolean checkTheExistenceOfABookByAuthorId(long bookAuthorId);

    List<Book> getAllBooks();
}
