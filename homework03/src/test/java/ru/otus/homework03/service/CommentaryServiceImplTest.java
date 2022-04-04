package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework03.dao.CommentaryDao;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.generator.CommentaryGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса комментариев должен: ")
class CommentaryServiceImplTest {
    @Mock
    private CommentaryDao commentaryDao;

    private CommentaryService commentaryService;

    @BeforeEach
    void setUp() {
        commentaryService = new CommentaryServiceImpl(commentaryDao);
    }

    @Test
    @DisplayName("создать новый комментарий")
    void createNewCommentaryTest() {
        Commentary commentary = CommentaryGenerator.generateCommentary();
        when(commentaryDao.getCommentaryListByName(commentary.getName())).thenReturn(new ArrayList<>());
        commentaryService.createNewCommentary(commentary);
        verify(commentaryDao, times(1)).insert(commentary);
    }

    @Test
    @DisplayName("не создать новый комментарий, потому что он уже существует")
    void notCreateNewCommentaryTest() {
        Commentary commentary = CommentaryGenerator.generateCommentary();
        when(commentaryDao.getCommentaryListByName(commentary.getName())).thenReturn(CommentaryGenerator.generateCommentaryList());
        commentaryService.createNewCommentary(commentary);
        verify(commentaryDao, times(0)).insert(commentary);
    }

    @Test
    @DisplayName("найти ID комментария и вернуть его")
    void getIdByCommentaryNameWithFoundIdTest() {
        List<Commentary> commentaryList = CommentaryGenerator.generateCommentaryList();
        long expectedCommentaryId = 1;
        when(commentaryDao.getCommentaryListByName(any())).thenReturn(commentaryList);
        long actualCommentaryId = commentaryService.getIdByCommentaryName("good comment");
        assertEquals(expectedCommentaryId, actualCommentaryId);
        verify(commentaryDao, times(1)).getCommentaryListByName(any());
    }

    @Test
    @DisplayName("не найти ID комментария и вернуть 0")
    void getIdByCommentaryNameWithNotFoundIdTest() {
        long expectedCommentaryId = 0;
        when(commentaryDao.getCommentaryListByName(any())).thenReturn(new ArrayList<>());
        long actualCommentaryId = commentaryService.getIdByCommentaryName("good comment");
        assertEquals(expectedCommentaryId, actualCommentaryId);
        verify(commentaryDao, times(1)).getCommentaryListByName(any());
    }

    @Test
    @DisplayName("удалить комментарий по ID")
    void deleteCommentaryTest() {
        commentaryService.deleteCommentary(1L);
        verify(commentaryDao, times(1)).deleteCommentaryById(1L);
    }

    @Test
    @DisplayName("вернуть список всех комментариев по ID книги")
    void getAllCommentariesByBookIdTest() {
        List<Commentary> expectedCommentaryList = CommentaryGenerator.generateCommentaryList();
        when(commentaryDao.getAllCommentariesByBookId(1L)).thenReturn(expectedCommentaryList);
        List<Commentary> actualCommentaryList = commentaryService.getAllCommentariesByBookId(1L);
        assertEquals(expectedCommentaryList, actualCommentaryList);
        verify(commentaryDao, times(1)).getAllCommentariesByBookId(1L);
    }
}