package ru.otus.homework03.service;

import ru.otus.homework03.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<Genre> findGenreById(long id);

    long getIdByGenreName(String name);

    Genre createNewGenre(Genre genre);

    void deleteGenre(long genreId);

    List<Genre> getAllGenres();
}
