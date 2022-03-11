package ru.otus.homework03.service;

import ru.otus.homework03.domain.Author;

import java.util.List;

public interface AuthorService {
    long getIdByAuthorNameAndSurname(String name, String surname);

    void createNewAuthor(Author author);

    void deleteAuthor(long authorId);

    List<Author> getAllAuthors();
}
