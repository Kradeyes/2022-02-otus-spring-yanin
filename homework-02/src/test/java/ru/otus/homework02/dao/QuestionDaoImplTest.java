package ru.otus.homework02.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework02.config.QuestionsAndAnswersConfig;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.generator.QuestionGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс дао с вопросами должен: ")
class QuestionDaoImplTest {
    @Mock
    private QuestionsAndAnswersConfig config;

    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl(config);
    }

    @Test
    @DisplayName("получить список вопросов")
    void receiveQuestion() {
        when(config.getQuestionsAndAnswers()).thenReturn("/questions/questions_and_answers.csv");
        Question expectedQuestion = QuestionGenerator.generateQuestion();
        Question realQuestion = questionDao.receiveQuestion("US");
        verify(config, times(1)).getQuestionsAndAnswers();
        assertEquals(expectedQuestion, realQuestion);
    }
}