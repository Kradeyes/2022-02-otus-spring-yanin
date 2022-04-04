package ru.otus.homework03.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Book {
    private final long id;
    private final String bookTitle;
    private final long bookAuthorId;
    private final long bookGenreId;
}
