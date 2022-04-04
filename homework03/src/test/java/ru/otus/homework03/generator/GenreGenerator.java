package ru.otus.homework03.generator;

import ru.otus.homework03.domain.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreGenerator {
    public static List<Genre> generateGenresList() {
        List<Genre> genresList = new ArrayList<>();
        Genre genre = new Genre(1, "Horror");
        genresList.add(genre);
        return genresList;
    }
}
