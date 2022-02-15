package ru.otus.homework02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework02.dao.AnswerDao;
import ru.otus.homework02.dao.QuestionDao;
import ru.otus.homework02.dao.StudentDao;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.Student;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс тестирования студентов должен: ")
class StudentTestServiceImplTest {
    @Mock
    private QuestionDao questionDao;
    @Mock
    private StudentDao studentDao;
    @Mock
    private AnswerDao answerDao;

    private StudentTestService studentTestService;

    @BeforeEach
    void setUp() {
        studentTestService = new StudentTestServiceImpl(questionDao, studentDao, answerDao);
    }

    @Test
    @DisplayName("должен сгененрировать список вопросов")
    void generateQuestionTest() {
        HashMap<String, List<String>> questionMap = new HashMap<>();
        questionMap.put("How old are you", List.of("10", "20", "30"));
        Question question = new Question(questionMap);
        when(questionDao.receiveQuestion()).thenReturn(question);
        studentTestService.generateQuestion();
        verify(questionDao, times(1)).receiveQuestion();
    }

    @Test
    @DisplayName("должен установить имя и фамилию студенту")
    void setStudentNameAndSurnameTest() {
        Student student = new Student("Ivan", "Ivanov");
        when(studentDao.setNameAndSurname(any(), any())).thenReturn(student);
        studentTestService.setStudentNameAndSurname("Ivan", "Ivanov");
        verify(studentDao, times(1)).setNameAndSurname("Ivan", "Ivanov");
    }

    @Test
    @DisplayName("должен получить кол-во правильных ответов")
    void getStudentScoreTest() {
        when(studentDao.getStudentScore(any())).thenReturn(5);
        int realScore = studentTestService.getStudentScore(any());
        assertEquals(5, realScore);
        verify(studentDao, times(1)).getStudentScore(any());
    }

    @Test
    @DisplayName("должен увеличить кол-во правильных ответов")
    void checkStudentAnswerTest() {
        String[] answerMassive = new String[]{"rightAnswer"};
        Answer answer = new Answer(answerMassive);
        Student student = new Student("Ivan", "Ivanov");
        when(answerDao.receiveAnswer()).thenReturn(answer);

        studentTestService.checkStudentAnswer("rightAnswer", student);

        verify(answerDao, times(1)).receiveAnswer();
        verify(studentDao, times(1)).increaseStudentScore(student);
    }
}