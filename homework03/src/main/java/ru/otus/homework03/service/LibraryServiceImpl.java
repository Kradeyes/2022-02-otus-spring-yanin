package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final AuthorService authorService;
    private final BookService bookService;
    private final GenreService genreService;
    private final CommentaryService commentaryService;

    @Override
    @Transactional
    public void createNewGenre(Genre genre) {
        genreService.createNewGenre(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(String genreName, MessageSource messageSource) {
        long genreId = genreService.getIdByGenreName(genreName);
        if (!bookService.checkTheExistenceOfABookByGenreId(genreId) && genreId != 0) {
            genreService.deleteGenre(genreId);
        } else {
            System.out.println(messageSource.getMessage("error.deleteGenre", null, Locale.getDefault()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void showAllGenres(MessageSource messageSource) {
        List<Genre> genreList = genreService.getAllGenres();
        if (!genreList.isEmpty()) {
            for (Genre genre : genreList) {
                System.out.println(genre);
            }
        } else {
            System.out.println(messageSource.getMessage("error.showAllGenres", null, Locale.getDefault()));
        }
    }

    @Override
    @Transactional
    public void createNewAuthor(Author author) {
        authorService.createNewAuthor(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(String authorName, String authorSurname, MessageSource messageSource) {
        long authorId = authorService.getIdByAuthorNameAndSurname(authorName, authorSurname);
        if (!bookService.checkTheExistenceOfABookByAuthorId(authorId) && authorId != 0) {
            authorService.deleteAuthor(authorId);
        } else {
            System.out.println(messageSource.getMessage("error.deleteAuthor", null, Locale.getDefault()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void showAllAuthors(MessageSource messageSource) {
        List<Author> authorList = authorService.getAllAuthors();
        if (!authorList.isEmpty()) {
            for (Author author : authorList) {
                System.out.println(author);
            }
        } else {
            System.out.println(messageSource.getMessage("error.showAllAuthors", null, Locale.getDefault()));
        }
    }

    @Override
    @Transactional
    public void createNewBook(Book book) {
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
        bookService.createNewBook(book, existingAuthorOrGenre);
    }

    @Override
    @Transactional
    public void deleteBook(String bookTitle, String authorName, String authorSurname, String genreName, MessageSource messageSource) {
        long bookAuthorId = authorService.getIdByAuthorNameAndSurname(authorName, authorSurname);
        long bookGenreId = genreService.getIdByGenreName(genreName);
        long bookId = bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId(bookTitle, bookAuthorId, bookGenreId);
        if (bookId != 0) {
            bookService.deleteBook(bookId);
        } else {
            System.out.println(messageSource.getMessage("error.deleteBook", null, Locale.getDefault()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void showAllBooks(MessageSource messageSource) {
        List<Book> bookList = bookService.getAllBooks();
        if (!bookList.isEmpty()) {
            for (Book book : bookList) {
                System.out.println(book);
            }
        } else {
            System.out.println(messageSource.getMessage("error.showAllBooks", null, Locale.getDefault()));
        }
    }

    @Override
    @Transactional
    public void createNewCommentary(Commentary commentary, Book book, MessageSource messageSource) {
        long bookAuthorId = authorService.getIdByAuthorNameAndSurname(book.getAuthor().getName(), book.getAuthor().getSurname());
        long bookGenreId = genreService.getIdByGenreName(book.getGenre().getGenreName());
        long bookId = bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId(book.getBookTitle(), bookAuthorId, bookGenreId);
        if (bookId != 0) {
            book.setId(bookId);
            book.getAuthor().setId(bookAuthorId);
            book.getGenre().setId(bookGenreId);
            commentary.setBook(book);
            commentaryService.createNewCommentary(commentary);
        } else {
            System.out.println(messageSource.getMessage("error.createCommentary", null, Locale.getDefault()));
        }
    }

    @Override
    @Transactional
    public void deleteCommentary(String name, MessageSource messageSource) {
        long commentaryId = commentaryService.getIdByCommentaryName(name);
        if (commentaryId != 0) {
            commentaryService.deleteCommentary(commentaryId);
        } else {
            System.out.println(messageSource.getMessage("error.deleteCommentary", null, Locale.getDefault()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void showAllCommentariesByBookId(Book book, MessageSource messageSource) {
        long bookAuthorId = authorService.getIdByAuthorNameAndSurname(book.getAuthor().getName(), book.getAuthor().getSurname());
        long bookGenreId = genreService.getIdByGenreName(book.getGenre().getGenreName());
        long bookId = bookService.getIdByBookTitleAndBookAuthorIdAndBookGenreId(book.getBookTitle(), bookAuthorId, bookGenreId);
        List<Commentary> commentaries = commentaryService.getAllCommentariesByBookId(bookId);
        if (!commentaries.isEmpty()) {
            for (Commentary commentary : commentaries) {
                System.out.println(commentary);
            }
        } else {
            System.out.println(messageSource.getMessage("error.showAllCommentaries", null, Locale.getDefault()));
        }
    }
}
