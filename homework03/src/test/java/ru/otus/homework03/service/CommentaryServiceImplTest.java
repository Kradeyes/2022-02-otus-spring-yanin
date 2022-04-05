package ru.otus.homework03.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework03.exception.ImpossibilityCreationException;
import ru.otus.homework03.repository.CommentaryRepository;
import ru.otus.homework03.domain.Commentary;
import ru.otus.homework03.generator.CommentaryGenerator;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс сервиса комментариев должен: ")
class CommentaryServiceImplTest {
    @Mock
    private CommentaryRepository commentaryRepository;

    private CommentaryService commentaryService;

    @BeforeEach
    void setUp() {
        commentaryService = new CommentaryServiceImpl(commentaryRepository);
    }

    @Test
    @DisplayName("создать новый комментарий")
    void createNewCommentaryTest() {
        Commentary commentary = CommentaryGenerator.generateCommentary();
        when(commentaryRepository.findCommentaryByName(any())).thenReturn(Optional.empty());
        commentaryService.createNewCommentary(commentary);
        verify(commentaryRepository, times(1)).save(commentary);
    }

    @Test
    @DisplayName("не создать новый комментарий, потому что он уже существует")
    void notCreateNewCommentaryTest() {
        Commentary commentary = CommentaryGenerator.generateCommentary();
        Optional<Commentary> optionalCommentary = CommentaryGenerator.generateOptionalCommentary();
        when(commentaryRepository.findCommentaryByName(any())).thenReturn(optionalCommentary);
        assertThatThrownBy(() -> commentaryService.createNewCommentary(commentary))
                .isInstanceOf(ImpossibilityCreationException.class);
    }

    @Test
    @DisplayName("найти ID комментария и вернуть его")
    void getIdByCommentaryNameWithFoundIdTest() {
        Optional<Commentary> optionalCommentary = CommentaryGenerator.generateOptionalCommentary();
        long expectedCommentaryId = 1;
        when(commentaryRepository.findCommentaryByName(any())).thenReturn(optionalCommentary);
        long actualCommentaryId = commentaryService.getIdByCommentaryName("good comment");
        assertEquals(expectedCommentaryId, actualCommentaryId);
        verify(commentaryRepository, times(1)).findCommentaryByName(any());
    }

    @Test
    @DisplayName("не найти ID комментария и вернуть 0")
    void getIdByCommentaryNameWithNotFoundIdTest() {
        long expectedCommentaryId = 0;
        when(commentaryRepository.findCommentaryByName(any())).thenReturn(Optional.empty());
        long actualCommentaryId = commentaryService.getIdByCommentaryName("good comment");
        assertEquals(expectedCommentaryId, actualCommentaryId);
        verify(commentaryRepository, times(1)).findCommentaryByName(any());
    }

    @Test
    @DisplayName("удалить комментарий по ID")
    void deleteCommentaryTest() {
        commentaryService.deleteCommentary(1L);
        verify(commentaryRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("вернуть список всех комментариев по ID книги")
    void getAllCommentariesByBookIdTest() {
        List<Commentary> expectedCommentaryList = CommentaryGenerator.generateCommentaryList();
        when(commentaryRepository.findCommentariesByBook_Id(1L)).thenReturn(expectedCommentaryList);
        List<Commentary> actualCommentaryList = commentaryService.getAllCommentariesByBookId(1L);
        assertEquals(expectedCommentaryList, actualCommentaryList);
        verify(commentaryRepository, times(1)).findCommentariesByBook_Id(1L);
    }

    @Test
    @DisplayName("найти комментарий по ID")
    void findCommentaryById() {
        when(commentaryRepository.findById(any())).thenReturn(CommentaryGenerator.generateOptionalCommentary());
        commentaryService.findCommentaryById(1L);
        verify(commentaryRepository, times(1)).findById(any());
    }
}