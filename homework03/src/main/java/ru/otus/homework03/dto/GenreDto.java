package ru.otus.homework03.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework03.domain.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    private long id;
    private String genreName;

    public static Genre toDomainObject(GenreDto genreDto) {
        return new Genre(genreDto.getGenreName());
    }

    public static Genre toDomainObjectWithId(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getGenreName());
    }

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getGenreName());
    }
}
