package ru.otus.homework03.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.generator.CommentaryGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CommentaryDaoImpl.class)
@DisplayName("Dao для работы с комментариями должно: ")
class CommentaryDaoImplTest {

    @Autowired
    private CommentaryDao commentaryDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("добавлять комментарий в БД")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void insert() {
        Commentary expectedCommentary = CommentaryGenerator.generateCommentaryForDaoTests();
        commentaryDao.insert(expectedCommentary);
        Commentary actualCommentary = em.find(Commentary.class, 2L);
        assertEquals(expectedCommentary, actualCommentary);
    }

    @Test
    @DisplayName("найти список всех комментариев по ID книги")
    void getAllCommentariesByBookId() {
        Commentary expectedCommentary = CommentaryGenerator.generateCommentaryForDaoTests();
        expectedCommentary.setId(1L);
        Commentary actualCommentary = commentaryDao.getAllCommentariesByBookId(1L).get(0);
        assertEquals(expectedCommentary, actualCommentary);
    }

    @Test
    @DisplayName("найти список комментариев по названию")
    void getCommentaryListByName() {
        List<Commentary> expectedCommentaryList = CommentaryGenerator.generateCommentaryList();
        List<Commentary> actualCommentaryList = commentaryDao.getCommentaryListByName("good comment");
        assertEquals(expectedCommentaryList, actualCommentaryList);
    }

    @Test
    @DisplayName("удалять комментарий")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteCommentaryById() {
        commentaryDao.insert(CommentaryGenerator.generateCommentaryForDaoTests());
        commentaryDao.deleteCommentaryById(2);
        assertEquals(1, commentaryDao.getAllCommentariesByBookId(1L).size());
    }
}