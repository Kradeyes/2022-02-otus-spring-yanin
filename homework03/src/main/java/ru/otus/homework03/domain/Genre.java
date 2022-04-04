package ru.otus.homework03.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Genre {
    private final long id;
    private final String genreName;
}
