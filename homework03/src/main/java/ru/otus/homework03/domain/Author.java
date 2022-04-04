package ru.otus.homework03.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Author {
    private final long id;
    private final String name;
    private final String surname;
}