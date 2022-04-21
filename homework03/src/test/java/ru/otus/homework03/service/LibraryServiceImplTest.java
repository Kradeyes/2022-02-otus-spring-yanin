package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.exception.ImpossibilityDeleteException;
import ru.otus.homework03.generator.AuthorGenerator;
import ru.otus.homework03.generator.BookGenerator;
import ru.otus.homework03.generator.CommentaryGenerator;
import ru.otus.homework03.generator.GenreGenerator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса библиотеки должен: ")
class LibraryServiceImplTest {
    @Mock
    private AuthorService authorService;
    @Mock
    private BookService bookService;
    @Mock
    private GenreService genreService;
    @Mock
    private CommentaryService commentaryService;
    @Mock
    private MessageSource messageSource;

    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryServiceImpl(authorService, bookService, genreService, commentaryService);
    }

    @Test
    @DisplayName("создать новый жанр")
    void createNewGenreTest() {
        Genre genre = GenreGenerator.generateGenre();
        libraryService.createNewGenre(genre);
        verify(genreService, times(1)).createNewGenre(genre);
    }

    @Test
    @DisplayName("удалить жанр")
    void deleteGenreTest() {
        libraryService.deleteGenreById(1L);
        verify(genreService, times(1)).deleteGenre(1L);
    }

    @Test
    @DisplayName("не удалять жанр т.к. на нём завязана книга")
    void dontDeleteGenreBecauseGenreNotExistTest() {
        when(bookService.checkTheExistenceOfABookByGenreId(1L)).thenReturn(Boolean.TRUE);
        assertThatThrownBy(() -> libraryService.deleteGenreById(1L))
                .isInstanceOf(ImpossibilityDeleteException.class);
    }

    @Test
    @DisplayName("получить список жанров")
    void showAllGenres() {
        when(genreService.getAllGenres()).thenReturn(GenreGenerator.generateGenresList());
        libraryService.getAllGenres();
        verify(genreService, times(1)).getAllGenres();
    }
    @Test
    @DisplayName("найти жанр по ID")
    void getGenreById() {
        when(genreService.findGenreById(anyLong())).thenReturn(GenreGenerator.generateOptionalGenre());
        libraryService.getGenreById(1L);
        verify(genreService, times(1)).findGenreById(1L);
    }


    @Test
    @DisplayName("обновить жанр")
    void updateGenre() {
        Genre genreForUpdate = GenreGenerator.generateGenre();
        genreForUpdate.setId(1L);
        genreForUpdate.setGenreName("Drama");
        when(genreService.findGenreById(1L)).thenReturn(GenreGenerator.generateOptionalGenre());
        Genre updatedGenre = libraryService.updateGenre(genreForUpdate);
        assertEquals(genreForUpdate.getGenreName(), updatedGenre.getGenreName());
    }

    @Test
    @DisplayName("создать нового автора")
    void createNewAuthor() {
        Author author = AuthorGenerator.generateAuthor();
        libraryService.createNewAuthor(author);
        verify(authorService, times(1)).createNewAuthor(author);
    }

    @Test
    @DisplayName("найти автора по ID")
    void getAuthorById() {
        when(authorService.findAuthorById(anyLong())).thenReturn(AuthorGenerator.generateOptionalAuthor());
        libraryService.getAuthorById(1L);
        verify(authorService, times(1)).findAuthorById(1L);
    }

    @Test
    @DisplayName("получить список авторов")
    void getAllAuthors() {
        when(authorService.getAllAuthors()).thenReturn(AuthorGenerator.generateAuthorsList());
        libraryService.getAllAuthors();
        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    @DisplayName("обновить автора")
    void updateAuthor() {
        Author authorForUpdate = AuthorGenerator.generateAuthor();
        authorForUpdate.setId(1L);
        authorForUpdate.setSurname("Ivann");
        when(authorService.findAuthorById(1L)).thenReturn(AuthorGenerator.generateOptionalAuthor());
        Author updatedAuthor = libraryService.updateAuthor(authorForUpdate);
        assertEquals(authorForUpdate.getName(), updatedAuthor.getName());
    }

    @Test
    @DisplayName("удалить автора")
    void deleteAuthor() {
        libraryService.deleteAuthorById(1L);
        verify(authorService, times(1)).deleteAuthor(1L);
    }

    @Test
    @DisplayName("не удалять автора т.к. на нём завязана книга")
    void deleteAuthorFail() {
        when(bookService.checkTheExistenceOfABookByAuthorId(1L)).thenReturn(Boolean.TRUE);
        assertThatThrownBy(() -> libraryService.deleteAuthorById(1L))
                .isInstanceOf(ImpossibilityDeleteException.class);
    }

    @Test
    @DisplayName("создать новую книгу")
    void createNewBook() {
        Book book = BookGenerator.generateBook();
        when(authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov")).thenReturn(1L);
        when(genreService.getIdByGenreName("Horror")).thenReturn(1L);
        libraryService.createNewBook(book);
        verify(bookService, times(1)).createNewBook(book, Boolean.TRUE);
    }

    @Test
    @DisplayName("удалить книгу")
    void deleteBookTest() {
        libraryService.deleteBookById(1L);
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    @DisplayName("получить список всех книг")
    void showAllBooksTest() {
        when(bookService.getAllBooks()).thenReturn(BookGenerator.generateBooksList());
        libraryService.getAllBooks();
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    @DisplayName("создать новый комментарий")
    void createNewCommentaryTest() {
        Commentary finalCommentary = CommentaryGenerator.generateCommentary();
        when(commentaryService.createNewCommentary(any())).thenReturn(finalCommentary);
        libraryService.createNewCommentary(CommentaryGenerator.generateCommentary());
        verify(commentaryService, times(1)).createNewCommentary(finalCommentary);
    }

    @Test
    @DisplayName("удалить комментарий")
    void deleteCommentaryTest() {
        libraryService.deleteCommentaryById(1L);
        verify(commentaryService, times(1)).deleteCommentary(1L);
    }

    @Test
    @DisplayName("вывести список всех комментариев для книги")
    void showAllCommentaryForBookTest() {
        when(commentaryService.getAllCommentariesByBookId(anyLong())).thenReturn(CommentaryGenerator.generateCommentaryList());
        libraryService.getAllCommentariesByBookId(1L);
        verify(commentaryService, times(1)).getAllCommentariesByBookId(1L);
    }

    @Test
    @DisplayName("обновить книгу")
    void updateBook() {
        Book bookForUpdate = BookGenerator.generateBookWithIdForAll();
        bookForUpdate.setBookTitle("newTitle");
        when(bookService.findBookById(1L)).thenReturn(BookGenerator.generateOptionalBook());
        Book updatedBook = libraryService.updateBook(bookForUpdate);
        assertEquals(bookForUpdate.getBookTitle(), updatedBook.getBookTitle());
    }

    @Test
    @DisplayName("найти книгу по ID")
    void getBookById() {
        when(bookService.findBookById(anyLong())).thenReturn(BookGenerator.generateOptionalBook());
        libraryService.getBookById(1L);
        verify(bookService, times(1)).findBookById(1L);
    }

    @Test
    @DisplayName("обновить комментарий")
    void getCommentaryById() {
        Commentary commentaryForUpdate = CommentaryGenerator.generateOptionalCommentary().get();
        commentaryForUpdate.setName("new");
        when(commentaryService.findCommentaryById(1L)).thenReturn(CommentaryGenerator.generateOptionalCommentary());
        Commentary updatedCommentary = libraryService.updateCommentary(commentaryForUpdate);
        assertEquals(commentaryForUpdate.getName(), updatedCommentary.getName());
    }

    @Test
    @DisplayName("найти комментарий по ID")
    void updateCommentary() {
        when(commentaryService.findCommentaryById(anyLong())).thenReturn(CommentaryGenerator.generateOptionalCommentary());
        libraryService.getCommentaryById(1L);
        verify(commentaryService, times(1)).findCommentaryById(1L);
    }
}
