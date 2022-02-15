package ru.otus.homework02.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Student {
    private final String name;
    private final String surname;
    private int score;

}
