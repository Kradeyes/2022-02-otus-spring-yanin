package ru.otus.homework02.dao;

import ru.otus.homework02.domain.Student;

public interface StudentDao {
    Student setNameAndSurname(String name, String surname);

    void increaseStudentScore(Student student);

    int getStudentScore(Student student);
}
