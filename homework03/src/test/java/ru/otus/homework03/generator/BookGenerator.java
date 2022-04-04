package ru.otus.homework03.generator;


import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookGenerator {
    public static Book generateBookWithIdForAll() {
        Book book = new Book();
        book.setId(1);
        book.setBookTitle("someTitle");
        book.setAuthor(new Author(1, "Ivan", "Ivanov"));
        book.setGenre(new Genre(1, "Horror"));
        return book;
    }

    public static Book generateBook() {
        Book book = new Book();
        book.setBookTitle("someTitle");
        book.setAuthor(AuthorGenerator.generateAuthor());
        book.setGenre(GenreGenerator.generateGenre());
        return book;
    }

    public static List<Book> generateBooksList() {
        List<Book> bookList = new ArrayList<>();
        Book book = new Book(1, "someTitle", new Author(1, "Ivan", "Ivanov"), new Genre(1, "Horror"));
        bookList.add(book);
        return bookList;
    }

    public static Optional<Book> generateOptionalBook() {
        Book book = new Book(1, "someTitle", new Author(1, "Ivan", "Ivanov"), new Genre(1, "Horror"));
        return Optional.of(book);
    }
}
