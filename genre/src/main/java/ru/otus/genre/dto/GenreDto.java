package ru.otus.genre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreDto {
    private long id;
    private String genreName;
}
