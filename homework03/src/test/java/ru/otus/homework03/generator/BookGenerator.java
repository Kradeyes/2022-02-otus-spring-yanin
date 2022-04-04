package ru.otus.homework03.generator;


import ru.otus.homework03.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookGenerator {
    public static List<Book> generateBooksList() {
        List<Book> bookList = new ArrayList<>();
        Book book = new Book(1, "someTitle", 1, 1);
        bookList.add(book);
        return bookList;
    }
}
