package ru.otus.homework03.generator;

import ru.otus.homework03.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenreGenerator {
    public static Genre generateGenre() {
        Genre genre = new Genre();
        genre.setGenreName("Horror");
        return genre;
    }

    public static List<Genre> generateGenresList() {
        List<Genre> genresList = new ArrayList<>();
        Genre genre = new Genre(1, "Horror");
        genresList.add(genre);
        return genresList;
    }

    public static Optional<Genre> generateOptionalGenre() {
        Genre genre = new Genre(1, "Horror");
        return Optional.of(genre);
    }
}
