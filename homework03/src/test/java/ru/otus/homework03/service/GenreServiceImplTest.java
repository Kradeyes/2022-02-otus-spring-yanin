package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework03.exception.ImpossibilityCreationException;
import ru.otus.homework03.repository.GenreRepository;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.generator.GenreGenerator;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса жанров должен: ")
class GenreServiceImplTest {
    @Mock
    private GenreRepository genreRepository;

    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreRepository);
    }

    @Test
    @DisplayName("найти ID жанра и вернуть его")
    void getIdByGenreNameWithFoundIdTest() {
        Optional<Genre> genre = GenreGenerator.generateOptionalGenre();
        long expectedGenreId = 1;
        when(genreRepository.findGenreByGenreName(any())).thenReturn(genre);
        long actualGenreId = genreService.getIdByGenreName("Horror");
        assertEquals(expectedGenreId, actualGenreId);
        verify(genreRepository, times(1)).findGenreByGenreName(any());
    }

    @Test
    @DisplayName("не найти ID жанра и вернуть 0")
    void getIdByGenreNameWithNotFoundIdTest() {
        long expectedGenreId = 0;
        when(genreRepository.findGenreByGenreName(any())).thenReturn(Optional.empty());
        long actualGenreId = genreService.getIdByGenreName("Horror");
        assertEquals(expectedGenreId, actualGenreId);
        verify(genreRepository, times(1)).findGenreByGenreName(any());
    }

    @Test
    @DisplayName("создать новый жанр")
    void createNewGenreTest() {
        Genre genre = GenreGenerator.generateGenre();
        when(genreRepository.findGenreByGenreName(any())).thenReturn(Optional.empty());
        genreService.createNewGenre(genre);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    @DisplayName("не создать новый жанр, потому что он уже существует")
    void notCreateNewGenreTest() {
        Genre genre = GenreGenerator.generateGenre();
        Optional<Genre> optionalGenre = GenreGenerator.generateOptionalGenre();
        when(genreRepository.findGenreByGenreName(any())).thenReturn(optionalGenre);
        assertThatThrownBy(() -> genreService.createNewGenre(genre))
                .isInstanceOf(ImpossibilityCreationException.class);
    }

    @Test
    @DisplayName("удалить жанр по ID")
    void deleteGenreTest() {
        genreService.deleteGenre(1);
        verify(genreRepository, times(1)).deleteGenreById(1);
    }

    @Test
    @DisplayName("вернуть список всех жанров")
    void getAllGenresTest() {
        List<Genre> expectedGenresList = GenreGenerator.generateGenresList();
        when(genreRepository.findAll()).thenReturn(expectedGenresList);
        List<Genre> actualGenresList = genreService.getAllGenres();
        assertEquals(expectedGenresList, actualGenresList);
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("найти жанр по ID")
    void findGenreById() {
        when(genreRepository.findById(any())).thenReturn(GenreGenerator.generateOptionalGenre());
        genreService.findGenreById(1L);
        verify(genreRepository, times(1)).findById(any());
    }
}
