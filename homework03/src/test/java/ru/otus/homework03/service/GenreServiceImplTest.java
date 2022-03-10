package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework03.dao.GenreDao;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.generator.GenreGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса жанров должен: ")
class GenreServiceImplTest {
    @Mock
    private GenreDao genreDao;

    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreDao);
    }

    @Test
    @DisplayName("найти ID жанра и вернуть его")
    void getIdByGenreNameWithFoundIdTest() {
        List<Genre> genresList = GenreGenerator.generateGenresList();
        long expectedGenreId = 1;
        when(genreDao.getGenreListByGenreName(any())).thenReturn(genresList);
        long actualGenreId = genreService.getIdByGenreName("Horror");
        assertEquals(expectedGenreId, actualGenreId);
        verify(genreDao, times(1)).getGenreListByGenreName(any());
    }

    @Test
    @DisplayName("не найти ID жанра и вернуть 0")
    void getIdByGenreNameWithNotFoundIdTest() {
        long expectedGenreId = 0;
        when(genreDao.getGenreListByGenreName(any())).thenReturn(new ArrayList<>());
        long actualGenreId = genreService.getIdByGenreName("Horror");
        assertEquals(expectedGenreId, actualGenreId);
        verify(genreDao, times(1)).getGenreListByGenreName(any());
    }

    @Test
    @DisplayName("создать новый жанр")
    void createNewGenreTest() {
        when(genreDao.getAllGenres()).thenReturn(new ArrayList<>());
        genreService.createNewGenre("Horror");
        verify(genreDao, times(1)).insert(any());
    }

    @Test
    @DisplayName("не создать новый жанр, потому что он уже существует")
    void notCreateNewGenreTest() {
        List<Genre> genresList = GenreGenerator.generateGenresList();
        when(genreDao.getAllGenres()).thenReturn(genresList);
        genreService.createNewGenre("Horror");
        verify(genreDao, times(0)).insert(any());
    }

    @Test
    @DisplayName("удалить жанр по ID")
    void deleteGenreTest() {
        genreService.deleteGenre(1);
        verify(genreDao, times(1)).deleteGenreById(1);
    }

    @Test
    @DisplayName("вернуть список всех жанров")
    void getAllGenresTest() {
        List<Genre> expectedGenresList = GenreGenerator.generateGenresList();
        when(genreDao.getAllGenres()).thenReturn(expectedGenresList);
        List<Genre> actualGenresList = genreService.getAllGenres();
        assertEquals(expectedGenresList, actualGenresList);
        verify(genreDao, times(1)).getAllGenres();
    }
}