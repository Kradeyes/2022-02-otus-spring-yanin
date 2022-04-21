package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework03.repository.AuthorRepository;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.generator.AuthorGenerator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса авторов должен: ")
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository);
    }


    @Test
    @DisplayName("найти ID автора и вернуть его")
    void getIdByAuthorNameAndSurnameWithFoundIdTest() {
        Optional<Author> author = AuthorGenerator.generateOptionalAuthor();
        long expectedAuthorId = 1;
        when(authorRepository.findAuthorByNameAndSurname(any(), any())).thenReturn(author);
        long actualAuthorId = authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov");
        assertEquals(expectedAuthorId, actualAuthorId);
        verify(authorRepository, times(1)).findAuthorByNameAndSurname(any(), any());
    }

    @Test
    @DisplayName("не найти ID автора и вернуть 0")
    void getIdByAuthorNameAndSurnameWithNotFoundIdTest() {
        long expectedAuthorId = 0;
        when(authorRepository.findAuthorByNameAndSurname(any(), any())).thenReturn(Optional.empty());
        long actualAuthorId = authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov");
        assertEquals(expectedAuthorId, actualAuthorId);
        verify(authorRepository, times(1)).findAuthorByNameAndSurname(any(), any());
    }

    @Test
    @DisplayName("создать нового автора")
    void createNewAuthorTest() {
        Author author = AuthorGenerator.generateAuthor();
        when(authorRepository.findAuthorByNameAndSurname(any(), any())).thenReturn(Optional.empty());
        authorService.createNewAuthor(author);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("не создать нового автора, потому что он уже существует")
    void notCreateNewAuthorTest() {
        Author author = AuthorGenerator.generateAuthor();
        Optional<Author> optionalAuthor = AuthorGenerator.generateOptionalAuthor();
        when(authorRepository.findAuthorByNameAndSurname(any(), any())).thenReturn(optionalAuthor);
        authorService.createNewAuthor(author);
        verify(authorRepository, times(0)).save(author);
    }


    @Test
    @DisplayName("удалить автора по ID")
    void deleteAuthorTest() {
        authorService.deleteAuthor(1);
        verify(authorRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("вернуть список всех авторов")
    void getAllAuthorsTest() {
        List<Author> expectedAuthorsList = AuthorGenerator.generateAuthorsList();
        when(authorRepository.findAll()).thenReturn(expectedAuthorsList);
        List<Author> actualAuthorsList = authorService.getAllAuthors();
        assertEquals(expectedAuthorsList, actualAuthorsList);
        verify(authorRepository, times(1)).findAll();
    }
}
