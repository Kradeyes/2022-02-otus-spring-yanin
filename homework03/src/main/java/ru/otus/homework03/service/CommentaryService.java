package ru.otus.homework03.service;

import ru.otus.homework03.domain.Commentary;

import java.util.List;

public interface CommentaryService {

    void createNewCommentary(Commentary commentary);

    long getIdByCommentaryName(String name);

    void deleteCommentary(long commentaryId);

    List<Commentary> getAllCommentariesByBookId(long bookId);

}
