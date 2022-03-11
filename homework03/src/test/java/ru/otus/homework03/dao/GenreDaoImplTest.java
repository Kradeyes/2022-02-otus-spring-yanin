package ru.otus.homework03.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework03.domain.Genre;
import ru.otus.homework03.generator.GenreGenerator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(GenreDaoImpl.class)
@DisplayName("Dao для работы с жанрами должно: ")
class GenreDaoImplTest {

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("добавлять жанр в БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void insertTest() {
        Genre expectedGenre = GenreGenerator.generateGenre();
        genreDao.insert(expectedGenre);
        Genre actualGenre = em.find(Genre.class, 2L);
        assertEquals(expectedGenre, actualGenre);
    }

    @Test
    @DisplayName("найти список жанров по названию")
    void getGenreListByGenreNameTest() {
        List<Genre> expectedGenreList = GenreGenerator.generateGenresList();
        List<Genre> actualGenreList = genreDao.getGenreListByGenreName("Horror");
        assertEquals(expectedGenreList, actualGenreList);
    }

    @Test
    @DisplayName("возвращать ожидаемый список жанров")
    void getAllGenresTest() {
        List<Genre> actualGenreList = genreDao.getAllGenres();
        Genre expectedGenre = new Genre(1, "Horror");
        assertThat(actualGenreList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedGenre);
    }

    @Test
    @DisplayName("удалять жанр")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteGenreByIdTest() {
        genreDao.insert(GenreGenerator.generateGenre());
        genreDao.deleteGenreById(2);
        assertEquals(1, genreDao.getAllGenres().size());
    }
}