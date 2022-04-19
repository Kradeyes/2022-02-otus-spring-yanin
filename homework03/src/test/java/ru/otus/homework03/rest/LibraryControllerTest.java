package ru.otus.homework03.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.domain.Book;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.dto.AuthorDto;
import ru.otus.homework03.dto.BookDto;
import ru.otus.homework03.dto.CommentaryDto;
import ru.otus.homework03.dto.GenreDto;
import ru.otus.homework03.generator.AuthorGenerator;
import ru.otus.homework03.generator.BookGenerator;
import ru.otus.homework03.generator.CommentaryGenerator;
import ru.otus.homework03.generator.GenreGenerator;
import ru.otus.homework03.security.UserDetailsServiceImpl;
import ru.otus.homework03.service.LibraryService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
@DisplayName("Класс библиотечного контроллера должен: ")
class LibraryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @MockBean
    private LibraryService libraryService;
    @MockBean
    private MessageSource messageSource;
    @Autowired
    private ObjectMapper mapper;

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("создать нового автора")
    void createNewAuthor() throws Exception {
        Author author = new Author("Test", "Test");
        given(libraryService.createNewAuthor(author)).willReturn(AuthorGenerator.generateOptionalAuthor().get());
        AuthorDto expectedResult = AuthorDto.toDto(AuthorGenerator.generateOptionalAuthor().get());

        mvc.perform(post("/api/v1/author/").content(asJsonString(author))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }


    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:read"}
    )
    @DisplayName("возвращать автора по ID")
    void getAuthorById() throws Exception {
        given(libraryService.getAuthorById(1L)).willReturn(AuthorGenerator.generateOptionalAuthor());
        AuthorDto expectedResult = AuthorDto.toDto(AuthorGenerator.generateOptionalAuthor().get());

        mvc.perform(get("/api/v1/author/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:read"}
    )
    @DisplayName("возвращать список авторов")
    void getAllAuthors() throws Exception {
        given(libraryService.getAllAuthors()).willReturn(AuthorGenerator.generateAuthorsList());
        List<AuthorDto> expectedResult = AuthorGenerator.generateAuthorsList().stream()
                .map(AuthorDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/api/v1/author/"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("обновлять автора")
    void updateAuthor() throws Exception {
        Author author = new Author(1L, "Test", "Test");
        given(libraryService.getAuthorById(1L)).willReturn(AuthorGenerator.generateOptionalAuthor());
        given(libraryService.updateAuthor(author)).willReturn(author);
        AuthorDto expectedResult = AuthorDto.toDto(author);
        mvc.perform(put("/api/v1/author/").content(asJsonString(author))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("удалять автора по ID")
    void deleteAuthor() throws Exception {
        given(libraryService.getAuthorById(1L)).willReturn(AuthorGenerator.generateOptionalAuthor());
        AuthorDto expectedResult = AuthorDto.toDto(AuthorGenerator.generateOptionalAuthor().get());
        mvc.perform(delete("/api/v1/author/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("создать новый жанр")
    void createNewGenre() throws Exception {
        Genre genre = new Genre("Test");
        given(libraryService.createNewGenre(genre)).willReturn(GenreGenerator.generateOptionalGenre().get());
        GenreDto expectedResult = GenreDto.toDto(GenreGenerator.generateOptionalGenre().get());

        mvc.perform(post("/api/v1/genre/").content(asJsonString(genre))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:read"}
    )
    @DisplayName("возвращать жанр по ID")
    void getGenreById() throws Exception {
        given(libraryService.getGenreById(1L)).willReturn(GenreGenerator.generateOptionalGenre());
        GenreDto expectedResult = GenreDto.toDto(GenreGenerator.generateOptionalGenre().get());

        mvc.perform(get("/api/v1/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:read"}
    )
    @DisplayName("возвращать список жанров")
    void getAllGenres() throws Exception {
        given(libraryService.getAllGenres()).willReturn(GenreGenerator.generateGenresList());
        List<GenreDto> expectedResult = GenreGenerator.generateGenresList().stream()
                .map(GenreDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/api/v1/genre/"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("обновлять жанр")
    void updateGenre() throws Exception {
        Genre genre = new Genre(1L, "Test");
        given(libraryService.getGenreById(1L)).willReturn(GenreGenerator.generateOptionalGenre());
        given(libraryService.updateGenre(genre)).willReturn(genre);
        GenreDto expectedResult = GenreDto.toDto(genre);
        mvc.perform(put("/api/v1/genre/").content(asJsonString(genre))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("удалять жанр по ID")
    void deleteGenre() throws Exception {
        given(libraryService.getGenreById(1L)).willReturn(GenreGenerator.generateOptionalGenre());
        GenreDto expectedResult = GenreDto.toDto(GenreGenerator.generateOptionalGenre().get());
        mvc.perform(delete("/api/v1/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("создать новый комментарий")
    void createNewCommentary() throws Exception {
        Commentary commentary = new Commentary("Test", "Test", BookGenerator.generateBookWithIdForAll());
        CommentaryDto commentaryDto = new CommentaryDto(1L, "Test", "Test");
        given(libraryService.getBookById(1L)).willReturn(BookGenerator.generateOptionalBook());
        given(libraryService.createNewCommentary(commentary)).willReturn(CommentaryGenerator.generateOptionalCommentary().get());
        CommentaryDto expectedResult = CommentaryDto.toDto(CommentaryGenerator.generateOptionalCommentary().get());

        mvc.perform(post("/api/v1/commentary/").content(asJsonString(commentaryDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:read"}
    )
    @DisplayName("возвращать комментарий по ID")
    void getCommentaryById() throws Exception {
        given(libraryService.getCommentaryById(1L)).willReturn(CommentaryGenerator.generateOptionalCommentary());
        CommentaryDto expectedResult = CommentaryDto.toDto(CommentaryGenerator.generateOptionalCommentary().get());

        mvc.perform(get("/api/v1/commentary/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:read"}
    )
    @DisplayName("возвращать список комментариев по ID книги")
    void getAllCommentary() throws Exception {
        given(libraryService.getAllCommentariesByBookId(1L)).willReturn(CommentaryGenerator.generateCommentaryList());
        List<CommentaryDto> expectedResult = CommentaryGenerator.generateCommentaryList().stream()
                .map(CommentaryDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/api/v1/commentaries/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("обновлять комментарий")
    void updateCommentary() throws Exception {
        Commentary commentary = new Commentary(1, "Test", "Test", BookGenerator.generateBookWithIdForAll());
        CommentaryDto commentaryDto = new CommentaryDto(1L, 1, "Test", "Test");
        given(libraryService.getBookById(1L)).willReturn(BookGenerator.generateOptionalBook());
        given(libraryService.getCommentaryById(1L)).willReturn(CommentaryGenerator.generateOptionalCommentary());
        given(libraryService.updateCommentary(commentary)).willReturn(commentary);

        mvc.perform(put("/api/v1/commentary/").content(asJsonString(commentaryDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(commentaryDto)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("удалять комментарий по ID")
    void deleteCommentary() throws Exception {
        given(libraryService.getCommentaryById(1L)).willReturn(CommentaryGenerator.generateOptionalCommentary());
        CommentaryDto expectedResult = CommentaryDto.toDto(CommentaryGenerator.generateOptionalCommentary().get());
        mvc.perform(delete("/api/v1/commentary/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("создать книгу")
    void createNewBook() throws Exception {
        BookDto bookDto = new BookDto("someTitle", "Ivan", "Ivanov", "Horror");
        given(libraryService.createNewBook(BookDto.toDomainObject(bookDto))).willReturn(BookGenerator.generateOptionalBook().get());
        BookDto expectedResult = BookDto.toDto(BookGenerator.generateOptionalBook().get());

        mvc.perform(post("/api/v1/book/").content(asJsonString(bookDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:read"}
    )
    @DisplayName("возвращать книгу по ID")
    void getBookById() throws Exception {
        given(libraryService.getBookById(1L)).willReturn(BookGenerator.generateOptionalBook());
        BookDto expectedResult = BookDto.toDto(BookGenerator.generateOptionalBook().get());

        mvc.perform(get("/api/v1/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:read"}
    )
    @DisplayName("возвращать список книг")
    void getAllBooks() throws Exception {
        given(libraryService.getAllBooks()).willReturn(BookGenerator.generateBooksList());
        List<BookDto> expectedResult = BookGenerator.generateBooksList().stream()
                .map(BookDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/api/v1/book/"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("обновлять книгу")
    void updateBook() throws Exception {
        BookDto bookDto = new BookDto(1, "newTitle", "Test","Test","Test");
        Book book = new Book(1, "newTitle", new Author(1, "Test","Test"), new Genre(1,"Test"));
        given(libraryService.getBookById(1L)).willReturn(BookGenerator.generateOptionalBook());
        given(libraryService.updateBook(any())).willReturn(book);

        mvc.perform(put("/api/v1/book/").content(asJsonString(bookDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"developers:write"}
    )
    @DisplayName("удалять книгу по ID")
    void deleteBook() throws Exception {
        given(libraryService.getBookById(1L)).willReturn(BookGenerator.generateOptionalBook());
        BookDto expectedResult = BookDto.toDto(BookGenerator.generateOptionalBook().get());
        mvc.perform(delete("/api/v1/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"developers:read"}
    )
    @DisplayName("не удалять книгу из-за недостатка прав")
    void cantDeleteBookBecauseWrongAuthorities() throws Exception {
        given(libraryService.getBookById(1L)).willReturn(BookGenerator.generateOptionalBook());
        BookDto expectedResult = BookDto.toDto(BookGenerator.generateOptionalBook().get());
        mvc.perform(delete("/api/v1/book/1"))
                .andExpect(status().isForbidden());
    }
}