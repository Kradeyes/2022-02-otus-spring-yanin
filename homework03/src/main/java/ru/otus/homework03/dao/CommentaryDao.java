package ru.otus.homework03.dao;

import ru.otus.homework03.domain.Commentary;

import java.util.List;

public interface CommentaryDao {
    void insert(Commentary commentary);

    List<Commentary> getAllCommentariesByBookId(long bookId);

    void deleteCommentaryById(long id);

    List<Commentary> getCommentaryListByName(String name);
}
