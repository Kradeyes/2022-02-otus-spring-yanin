package ru.otus.homework02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.homework02.Main;
import ru.otus.homework02.dao.QuestionDao;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.generator.QuestionGenerator;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс запуска тестирования студентов должен: ")
class StudentTestStartServiceImplTest {

    @Mock
    private StudentTestServiceImpl studentTestService;

    private StudentTestStartService service;

    @BeforeEach
    void setUp() {
        service = new StudentTestStartServiceImpl(studentTestService);
    }

    @Test
    @DisplayName("запустить приложение и дойти до его завершения")
    void startTest() {
        String userInput = String.format("US%sIvan%sIvanov%s7%s2%s11%s366%sGosling",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n.messages");
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
        Student student = new Student("Ivan", "Ivanov");
        Question question = QuestionGenerator.generateQuestion();
        when(studentTestService.generateQuestion(any())).thenReturn(question);
        when(studentTestService.setStudentNameAndSurname(any(), any())).thenReturn(student);
        service.start(messageSource);
        verify(studentTestService, times(1)).generateQuestion(any());
        verify(studentTestService, times(1)).setStudentNameAndSurname(any(), any());
        verify(studentTestService, times(5)).checkStudentAnswer(any(), any(), any());
    }
}