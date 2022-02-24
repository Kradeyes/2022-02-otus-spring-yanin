package ru.otus.homework02.service;

import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.Student;

public interface StudentTestService {
    Question generateQuestion(String selectedLocale);

    Student setStudentNameAndSurname(String name, String surname);

    int getStudentScore(Student student);

    void checkStudentAnswer(String answer, Student student, String selectedLocale);
}
