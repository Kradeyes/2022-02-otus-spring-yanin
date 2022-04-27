package ru.otus.homework03.service;

import ru.otus.homework03.dto.GenreDto;

public interface GetGenreRestService {
    GenreDto getGenre(long id);
}
