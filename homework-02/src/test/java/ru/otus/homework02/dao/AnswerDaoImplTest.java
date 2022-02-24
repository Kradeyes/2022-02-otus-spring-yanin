package ru.otus.homework02.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework02.config.QuestionsAndAnswersConfig;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.service.StudentTestServiceImpl;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс дао с ответами должен: ")
class AnswerDaoImplTest {
    @Mock
    private QuestionsAndAnswersConfig config;

    private AnswerDao answerDao;

    @BeforeEach
    void setUp() {
        answerDao = new AnswerDaoImpl(config);
    }

    @Test
    @DisplayName("получить список правильных ответов")
    void receiveAnswerTest() {
        when(config.getRightAnswers()).thenReturn("/right_answers.csv");
        String[] expectedAnswerList = new String[]{"7", "Gosling", "2", "11", "366"};
        Answer realAnswerList = answerDao.receiveAnswer();
        assertEquals(Arrays.toString(expectedAnswerList), Arrays.toString(realAnswerList.getAnswers()));
        verify(config, times(1)).getRightAnswers();
    }
}