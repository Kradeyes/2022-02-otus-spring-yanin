package ru.otus.homework03.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework03.dao.CommentaryDao;
import ru.otus.homework03.domain.Commentary;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {
    private final CommentaryDao commentaryDao;

    @Override
    public void createNewCommentary(Commentary commentary) {
        if (!checkTheExistenceOfTheCommentary(commentary.getName())) {
            commentaryDao.insert(commentary);
        }
    }

    @Override
    public long getIdByCommentaryName(String name) {
        long id = 0;
        List<Commentary> commentaryList = commentaryDao.getCommentaryListByName(name);
        if (!commentaryList.isEmpty()) {
            id = commentaryList.get(0).getId();
        }
        return id;
    }

    @Override
    public void deleteCommentary(long commentaryId) {
        commentaryDao.deleteCommentaryById(commentaryId);
    }

    @Override
    public List<Commentary> getAllCommentariesByBookId(long bookId) {
        return commentaryDao.getAllCommentariesByBookId(bookId);
    }

    private boolean checkTheExistenceOfTheCommentary(String name) {
        List<Commentary> commentaryList = commentaryDao.getCommentaryListByName(name);
        return !commentaryList.isEmpty();
    }
}
