package ru.otus.homework03.dao;

import ru.otus.homework03.domain.Author;

import java.util.List;

public interface AuthorDao {
    void insert(String name, String surname);

    List<Author> getAuthorListByAuthorNameAndAuthorSurname(String name, String surname);

    List<Author> getAllAuthors();

    void deleteAuthorById(long id);
}
