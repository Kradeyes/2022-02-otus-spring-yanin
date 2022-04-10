package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.exception.ImpossibilityDeleteException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;
    private final CommentaryService commentaryService;

    @Override
    @Transactional
    public Genre createNewGenre(Genre genre) {
        return genreService.createNewGenre(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> getGenreById(long id) {
        return genreService.findGenreById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @Override
    @Transactional
    public Genre updateGenre(Genre genre) {
        Genre updatedGenre = genreService.findGenreById(genre.getId()).get();
        updatedGenre.setGenreName(genre.getGenreName());
        return updatedGenre;
    }

    @Override
    @Transactional
    public void deleteGenreById(long id) {
        if (!bookService.checkTheExistenceOfABookByGenreId(id)) {
            genreService.deleteGenre(id);
        } else {
            throw new ImpossibilityDeleteException();
        }
    }

    @Override
    @Transactional
    public Author createNewAuthor(Author author) {
        return authorService.createNewAuthor(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> getAuthorById(long id) {
        return authorService.findAuthorById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @Override
    @Transactional
    public Author updateAuthor(Author author) {
        Author updatedAuthor = authorService.findAuthorById(author.getId()).get();
        updatedAuthor.setName(author.getName());
        updatedAuthor.setSurname(author.getSurname());
        return updatedAuthor;
    }

    @Override
    @Transactional
    public void deleteAuthorById(long id) {
        if (!bookService.checkTheExistenceOfABookByAuthorId(id)) {
            authorService.deleteAuthor(id);
        } else {
            throw new ImpossibilityDeleteException();
        }
    }

    @Override
    @Transactional
    public Book createNewBook(Book book) {
        return bookService.createNewBook(book, existingAuthorOrGenreCheck(book));
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        Book updatedBook = bookService.findBookById(book.getId()).get();
        updatedBook.setBookTitle(book.getBookTitle());
        updatedBook.getAuthor().setName(book.getAuthor().getName());
        updatedBook.getAuthor().setSurname(book.getAuthor().getSurname());
        updatedBook.getGenre().setGenreName(book.getGenre().getGenreName());
        return updatedBook;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getBookById(long id) {
        return bookService.findBookById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        bookService.deleteBook(id);
    }

    @Override
    @Transactional
    public Commentary createNewCommentary(Commentary commentary) {
        return commentaryService.createNewCommentary(commentary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Commentary> getAllCommentariesByBookId(long bookId) {
        return commentaryService.getAllCommentariesByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Commentary> getCommentaryById(long id) {
        return commentaryService.findCommentaryById(id);
    }

    @Override
    @Transactional
    public Commentary updateCommentary(Commentary commentary) {
        Commentary updatedCommentary = commentaryService.findCommentaryById(commentary.getId()).get();
        updatedCommentary.setName(commentary.getName());
        updatedCommentary.setContent(commentary.getContent());
        return updatedCommentary;
    }

    @Override
    @Transactional
    public void deleteCommentaryById(long id) {
        commentaryService.deleteCommentary(id);
    }

    private boolean existingAuthorOrGenreCheck(Book book) {
        long authorId = authorService.getIdByAuthorNameAndSurname(book.getAuthor().getName(), book.getAuthor().getSurname());
        long genreId = genreService.getIdByGenreName(book.getGenre().getGenreName());
        boolean existingAuthorOrGenre = false;
        if (authorId != 0) {
            Author author = book.getAuthor();
            author.setId(authorId);
            book.setAuthor(author);
            existingAuthorOrGenre = true;
        }
        if (genreId != 0) {
            Genre genre = book.getGenre();
            genre.setId(genreId);
            book.setGenre(genre);
            existingAuthorOrGenre = true;
        }
        return existingAuthorOrGenre;
    }
}
