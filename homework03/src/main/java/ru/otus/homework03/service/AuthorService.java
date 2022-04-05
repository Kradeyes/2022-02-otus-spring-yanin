package ru.otus.homework03.service;

import ru.otus.homework03.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> findAuthorById(long authorId);

    long getIdByAuthorNameAndSurname(String name, String surname);

    Author createNewAuthor(Author author);

    void deleteAuthor(long authorId);

    List<Author> getAllAuthors();
}
