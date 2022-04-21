package ru.otus.homework03.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.generator.BookGenerator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("класс для работы с репозиторием комментариев должен: ")
class CommentaryRepositoryTest {

    @Autowired
    private CommentaryRepository commentaryRepository;

    @Test
    @DisplayName("находить комментарий в БД пой ID книги")
    void findCommentariesByBook_Id() {
        List<Commentary> commentaries = commentaryRepository.findCommentariesByBook_Id(1L);
        assertEquals(3, commentaries.size());
    }

    @Test
    @DisplayName("удалять комментарий из БД")
    void deleteById() {
        commentaryRepository.save(new Commentary(2,"bad","bad", BookGenerator.generateBookWithIdForAll()));
        Optional<Commentary> savedCommentary = commentaryRepository.findCommentaryByName("bad");
        assertEquals(Boolean.TRUE, savedCommentary.isPresent());
        commentaryRepository.deleteById(2L);
        Optional<Commentary> deletedCommentary = commentaryRepository.findCommentaryByName("bad");
        assertEquals(Boolean.FALSE, deletedCommentary.isPresent());
    }

    @Test
    @DisplayName("находить комментарий в БД")
    void findCommentaryByName() {
        Optional<Commentary> commentary = commentaryRepository.findCommentaryByName("good comment");
        assertEquals(Boolean.TRUE, commentary.isPresent());
    }
}