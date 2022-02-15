package ru.otus.homework02.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework02.domain.Student;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
@DisplayName("Класс дао с студентами должен: ")
class StudentDaoImplTest {

    private StudentDao studentDao;
    private Student student;

    @BeforeEach
    void setUp() {
        studentDao = new StudentDaoImpl();
        student = new Student("Ivan", "Ivanov");
    }

    @Test
    @DisplayName("установить имя и фамилию студенту")
    void setNameAndSurname() {
        Student realStudent = studentDao.setNameAndSurname("Ivan", "Ivanov");
        assertEquals(student.getName(), realStudent.getName());
        assertEquals(student.getSurname(), realStudent.getSurname());
    }

    @Test
    @DisplayName("увеличить кол-во правильных ответов")
    void increaseStudentScore() {
        studentDao.increaseStudentScore(student);
        assertEquals(1, student.getScore());
    }

    @Test
    @DisplayName("получить кол-во правильных ответов")
    void getStudentScore() {
        int realScore = studentDao.getStudentScore(student);
        assertEquals(0, realScore);
    }
}