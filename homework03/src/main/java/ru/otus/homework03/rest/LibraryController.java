package ru.otus.homework03.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.dto.AuthorDto;
import ru.otus.homework03.dto.BookDto;
import ru.otus.homework03.dto.CommentaryDto;
import ru.otus.homework03.dto.GenreDto;
import ru.otus.homework03.exception.*;
import ru.otus.homework03.service.LibraryService;


import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("api/v1")
@RestController
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;
    private final MessageSource messageSource;

    @PostMapping(path = "/author/")
    public AuthorDto createNewAuthor(@RequestBody AuthorDto authorDto) {
        return AuthorDto.toDto(libraryService.createNewAuthor(AuthorDto.toDomainObject(authorDto)));
    }

    @GetMapping(value = "/author/{id}")
    public AuthorDto getAuthorById(@PathVariable("id") long id) {
        Optional<Author> author = libraryService.getAuthorById(id);
        if (author.isEmpty()) {
            throw new NotFoundException();
        }
        return AuthorDto.toDto(author.get());
    }

    @GetMapping(value = "/author/")
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = libraryService.getAllAuthors();
        if (authors.isEmpty()) {
            throw new NotFoundException();
        }
        return authors.stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping(path = "/author/")
    public AuthorDto updateAuthor(@RequestBody AuthorDto authorDto) {
        Optional<Author> author = libraryService.getAuthorById(authorDto.getId());
        if (author.isEmpty()) {
            throw new ImpossibilityUpdateException();
        }
        return AuthorDto.toDto(libraryService.updateAuthor(AuthorDto.toDomainObjectWithId(authorDto)));
    }

    @DeleteMapping(path = "/author/{id}")
    public AuthorDto deleteAuthor(@PathVariable("id") long id) {
        Optional<Author> author = libraryService.getAuthorById(id);
        if (author.isEmpty()) {
            throw new ImpossibilityDeleteException();
        }
        libraryService.deleteAuthorById(id);
        return AuthorDto.toDto(author.get());
    }

    @PostMapping(path = "/genre/")
    public GenreDto createNewGenre(@RequestBody GenreDto genreDto) {
        return GenreDto.toDto(libraryService.createNewGenre(GenreDto.toDomainObject(genreDto)));
    }

    @GetMapping(value = "/genre/{id}")
    public GenreDto getGenreById(@PathVariable("id") long id) {
        Optional<Genre> genre = libraryService.getGenreById(id);
        if (genre.isEmpty()) {
            throw new NotFoundException();
        }
        return GenreDto.toDto(genre.get());
    }

    @GetMapping(value = "/genre/")
    public List<GenreDto> getAllGenres() {
        List<Genre> genres = libraryService.getAllGenres();
        if (genres.isEmpty()) {
            throw new NotFoundException();
        }
        return genres.stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping(path = "/genre/")
    public GenreDto updateGenre(@RequestBody GenreDto genreDto) {
        Optional<Genre> genre = libraryService.getGenreById(genreDto.getId());
        if (genre.isEmpty()) {
            throw new ImpossibilityUpdateException();
        }
        return GenreDto.toDto(libraryService.updateGenre(GenreDto.toDomainObjectWithId(genreDto)));
    }

    @DeleteMapping(path = "/genre/{id}")
    public GenreDto deleteGenre(@PathVariable("id") long id) {
        Optional<Genre> genre = libraryService.getGenreById(id);
        if (genre.isEmpty()) {
            throw new ImpossibilityDeleteException();
        }
        libraryService.deleteGenreById(id);
        return GenreDto.toDto(genre.get());
    }

    @PostMapping(path = "/commentary/")
    public CommentaryDto createNewCommentary(@RequestBody CommentaryDto commentaryDto) {
        Optional<Book> book = libraryService.getBookById(commentaryDto.getBookId());
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        return CommentaryDto.toDto(libraryService.createNewCommentary(CommentaryDto.toDomainObject(commentaryDto, book.get())));
    }

    @GetMapping(value = "/commentary/{id}")
    public CommentaryDto getCommentaryById(@PathVariable("id") long id) {
        Optional<Commentary> commentary = libraryService.getCommentaryById(id);
        if (commentary.isEmpty()) {
            throw new NotFoundException();
        }
        return CommentaryDto.toDto(commentary.get());
    }

    @GetMapping(value = "/commentaries/{id}")
    public List<CommentaryDto> getAllCommentary(@PathVariable("id") long id) {
        List<Commentary> commentaries = libraryService.getAllCommentariesByBookId(id);
        if (commentaries.isEmpty()) {
            throw new NotFoundException();
        }
        return commentaries.stream()
                .map(CommentaryDto::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping(path = "/commentary/")
    public CommentaryDto updateCommentary(@RequestBody CommentaryDto commentaryDto) {
        Optional<Book> book = libraryService.getBookById(commentaryDto.getBookId());
        if (book.isEmpty()) {
            throw new BookNotFoundException();
        }
        Optional<Commentary> commentary = libraryService.getCommentaryById(commentaryDto.getId());
        if (commentary.isEmpty()) {
            throw new ImpossibilityUpdateException();
        }
        return CommentaryDto.toDto(libraryService.updateCommentary(CommentaryDto.toDomainObjectWithId(commentaryDto, book.get())));
    }

    @DeleteMapping(path = "/commentary/{id}")
    public CommentaryDto deleteCommentary(@PathVariable("id") long id) {
        Optional<Commentary> commentary = libraryService.getCommentaryById(id);
        if (commentary.isEmpty()) {
            throw new ImpossibilityDeleteException();
        }
        libraryService.deleteCommentaryById(id);
        return CommentaryDto.toDto(commentary.get());
    }

    @PostMapping(path = "/book/")
    public BookDto createNewBook(@RequestBody BookDto bookDto) {
        return BookDto.toDto(libraryService.createNewBook(BookDto.toDomainObject(bookDto)));
    }

    @GetMapping(value = "/book/{id}")
    public BookDto getBookById(@PathVariable("id") long id) {
        Optional<Book> book = libraryService.getBookById(id);
        if (book.isEmpty()) {
            throw new NotFoundException();
        }
        return BookDto.toDto(book.get());
    }

    @GetMapping(value = "/book/")
    public List<BookDto> getAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            throw new NotFoundException();
        }
        return books.stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping(path = "/book/")
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        Optional<Book> book = libraryService.getBookById(bookDto.getId());
        if (book.isEmpty()) {
            throw new ImpossibilityUpdateException();
        }
        return BookDto.toDto(libraryService.updateBook(BookDto.toDomainObjectWithId(bookDto)));
    }

    @DeleteMapping(path = "/book/{id}")
    public BookDto deleteBook(@PathVariable("id") long id) {
        Optional<Book> book = libraryService.getBookById(id);
        if (book.isEmpty()) {
            throw new ImpossibilityDeleteException();
        }
        libraryService.deleteBookById(id);
        return BookDto.toDto(book.get());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body(messageSource.getMessage("error.EntityNotFound", null, Locale.getDefault()));
    }

    @ExceptionHandler(ImpossibilityCreationException.class)
    public ResponseEntity<String> impossibilityCreation(ImpossibilityCreationException ex) {
        return ResponseEntity.badRequest().body(messageSource.getMessage("error.ImpossibilityCreation", null, Locale.getDefault()));
    }

    @ExceptionHandler(ImpossibilityUpdateException.class)
    public ResponseEntity<String> impossibilityUpdate(ImpossibilityUpdateException ex) {
        return ResponseEntity.badRequest().body(messageSource.getMessage("error.ImpossibilityUpdate", null, Locale.getDefault()));
    }

    @ExceptionHandler(ImpossibilityDeleteException.class)
    public ResponseEntity<String> impossibilityDelete(ImpossibilityDeleteException ex) {
        return ResponseEntity.badRequest().body(messageSource.getMessage("error.ImpossibilityDelete", null, Locale.getDefault()));
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(BookNotFoundException ex) {
        return ResponseEntity.badRequest().body(messageSource.getMessage("error.BookNotFound", null, Locale.getDefault()));
    }
}
