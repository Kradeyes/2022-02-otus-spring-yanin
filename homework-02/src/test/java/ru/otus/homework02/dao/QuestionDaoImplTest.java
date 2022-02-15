package ru.otus.homework02.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework02.config.QuestionsAndAnswersConfig;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;

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
        when(config.getQuestionsAndAnswers()).thenReturn("/questions_and_answers.csv");
        Question expectedQuestion = generateExpectedQuestion();
        Question realQuestion = questionDao.receiveQuestion();
        verify(config, times(1)).getQuestionsAndAnswers();
        assertEquals(expectedQuestion, realQuestion);
    }

    private Question generateExpectedQuestion() {
        HashMap<String, List<String>> questionMap = new HashMap<>();
        questionMap.put("How many continents are there on earth?", List.of("6","7","8"));
        questionMap.put("What is the square root of 4?", List.of("3","2","1"));
        questionMap.put("How many people in 1 soccer team?", List.of("10","13","11"));
        questionMap.put("How many days in a leap year?", List.of("366","365","364"));
        questionMap.put("Who created the java language?", List.of("Gosling","Gates","Putin"));
        return new Question(questionMap);
    }
}