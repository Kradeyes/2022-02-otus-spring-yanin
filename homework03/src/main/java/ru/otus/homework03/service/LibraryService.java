package ru.otus.homework03.service;

import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    Genre createNewGenre(Genre genre);

    Optional<Genre> getGenreById(long id);

    List<Genre> getAllGenres();

    Genre updateGenre(Genre genre);

    void deleteGenreById(long id);

    Author createNewAuthor(Author author);

    Optional<Author> getAuthorById(long id);

    List<Author> getAllAuthors();

    Author updateAuthor(Author author);

    void deleteAuthorById(long id);

    Book createNewBook(Book book);

    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    Book updateBook(Book book);

    void deleteBookById(long id);

    Commentary createNewCommentary(Commentary commentary);

    Optional<Commentary> getCommentaryById(long id);

    List<Commentary> getAllCommentariesByBookId(long id);

    Commentary updateCommentary(Commentary commentary);

    void deleteCommentaryById(long id);
}
