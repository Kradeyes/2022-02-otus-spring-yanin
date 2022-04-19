package ru.otus.homework03.service;

import ru.otus.homework03.domain.Commentary;

import java.util.List;
import java.util.Optional;

public interface CommentaryService {
    Commentary createNewCommentary(Commentary commentary);

    Optional<Commentary> findCommentaryById(long commentaryId);

    long getIdByCommentaryName(String name);

    void deleteCommentary(long commentaryId);

    List<Commentary> getAllCommentariesByBookId(long bookId);
}
