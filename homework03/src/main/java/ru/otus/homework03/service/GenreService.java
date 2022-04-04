package ru.otus.homework03.service;

import ru.otus.homework03.domain.Genre;

import java.util.List;

public interface GenreService {
    long getIdByGenreName(String name);

    void createNewGenre(Genre genre);

    void deleteGenre(long genreId);

    List<Genre> getAllGenres();
}
