package ru.otus.homework02.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework02.domain.Student;

@Component
@RequiredArgsConstructor
public class StudentDaoImpl implements StudentDao {

    @Override
    public Student setNameAndSurname(String name, String surname) {
        return new Student(name, surname);
    }

    @Override
    public void increaseStudentScore(Student student) {
        student.setScore(student.getScore() + 1);
    }

    @Override
    public int getStudentScore(Student student) {
        return student.getScore();
    }
}
