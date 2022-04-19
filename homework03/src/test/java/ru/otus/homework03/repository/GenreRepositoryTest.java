package ru.otus.homework03.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework03.domain.Genre;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("класс для работы с репозиторием жанров должен: ")
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;


    @Test
    @DisplayName("находить жанр в БД")
    void findGenreByGenreNameTest() {
        Optional<Genre> genre = genreRepository.findGenreByGenreName("Horror");
        assertEquals(Boolean.TRUE, genre.isPresent());
    }

    @Test
    @DisplayName("находить удалять жанр из БД")
    void deleteGenreByIdTest() {
        genreRepository.save(new Genre(3, "Horror1"));
        Optional<Genre> savedGenre = genreRepository.findGenreByGenreName("Horror1");
        assertEquals(Boolean.TRUE, savedGenre.isPresent());
        genreRepository.deleteById(3L);
        Optional<Genre> deletedGenre = genreRepository.findGenreByGenreName("Horror1");
        assertEquals(Boolean.FALSE, deletedGenre.isPresent());
    }
}