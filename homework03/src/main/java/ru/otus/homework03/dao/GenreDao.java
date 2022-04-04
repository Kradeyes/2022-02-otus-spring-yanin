package ru.otus.homework03.dao;

import ru.otus.homework03.domain.Genre;

import java.util.List;

public interface GenreDao {
    void insert(Genre genre);

    List<Genre> getGenreListByGenreName(String name);

    List<Genre> getAllGenres();

    void deleteGenreById(long id);
}
