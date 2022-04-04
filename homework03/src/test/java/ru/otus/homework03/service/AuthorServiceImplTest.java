package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework03.dao.AuthorDao;
import ru.otus.homework03.domain.Author;
import ru.otus.homework03.generator.AuthorGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса авторов должен: ")
class AuthorServiceImplTest {
    @Mock
    private AuthorDao authorDao;

    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorDao);
    }


    @Test
    @DisplayName("найти ID автора и вернуть его")
    void getIdByAuthorNameAndSurnameWithFoundIdTest() {
        List<Author> authorList = AuthorGenerator.generateAuthorsList();
        long expectedAuthorId = 1;
        when(authorDao.getAuthorListByAuthorNameAndAuthorSurname(any(), any())).thenReturn(authorList);
        long actualAuthorId = authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov");
        assertEquals(expectedAuthorId, actualAuthorId);
        verify(authorDao, times(1)).getAuthorListByAuthorNameAndAuthorSurname(any(), any());
    }

    @Test
    @DisplayName("не найти ID автора и вернуть 0")
    void getIdByAuthorNameAndSurnameWithNotFoundIdTest() {
        List<Author> authorList = new ArrayList<>();
        long expectedAuthorId = 0;
        when(authorDao.getAuthorListByAuthorNameAndAuthorSurname(any(), any())).thenReturn(authorList);
        long actualAuthorId = authorService.getIdByAuthorNameAndSurname("Ivan", "Ivanov");
        assertEquals(expectedAuthorId, actualAuthorId);
        verify(authorDao, times(1)).getAuthorListByAuthorNameAndAuthorSurname(any(), any());
    }

    @Test
    @DisplayName("создать нового автора")
    void createNewAuthorTest() {
        when(authorDao.getAllAuthors()).thenReturn(new ArrayList<>());
        authorService.createNewAuthor("Ivan", "Ivanov");
        verify(authorDao, times(1)).insert(any(), any());
    }

    @Test
    @DisplayName("не создать нового автора, потому что он уже существует")
    void notCreateNewAuthorTest() {
        List<Author> authorList = AuthorGenerator.generateAuthorsList();
        when(authorDao.getAllAuthors()).thenReturn(authorList);
        authorService.createNewAuthor("Ivan", "Ivanov");
        verify(authorDao, times(0)).insert(any(), any());
    }


    @Test
    @DisplayName("удалить автора по ID")
    void deleteAuthorTest() {
        authorService.deleteAuthor(1);
        verify(authorDao, times(1)).deleteAuthorById(1);
    }

    @Test
    @DisplayName("вернуть список всех авторов")
    void getAllAuthorsTest() {
        List<Author> expectedAuthorsList = AuthorGenerator.generateAuthorsList();
        when(authorDao.getAllAuthors()).thenReturn(expectedAuthorsList);
        List<Author> actualAuthorsList = authorService.getAllAuthors();
        assertEquals(expectedAuthorsList, actualAuthorsList);
        verify(authorDao, times(1)).getAllAuthors();
    }
}
