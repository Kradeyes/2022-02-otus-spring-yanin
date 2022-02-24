package ru.otus.homework02.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework02.dao.AnswerDao;
import ru.otus.homework02.dao.QuestionDao;
import ru.otus.homework02.dao.StudentDao;
import ru.otus.homework02.domain.Answer;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.Student;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class StudentTestServiceImpl implements StudentTestService {

    private final QuestionDao questionDao;
    private final StudentDao studentDao;
    private final AnswerDao answerDao;

    @Override
    public Question generateQuestion(String selectedLocale) {
        return questionDao.receiveQuestion(selectedLocale);
    }

    @Override
    public Student setStudentNameAndSurname(String name, String surname) {
        return studentDao.setNameAndSurname(name, surname);
    }

    @Override
    public int getStudentScore(Student student) {
        return studentDao.getStudentScore(student);
    }

    @Override
    public void checkStudentAnswer(String answer, Student student, String selectedLocale) {
        Answer rightAnswers = answerDao.receiveAnswer(selectedLocale);
        if (Arrays.asList(rightAnswers.getAnswers()).contains(answer)) {
            increaseStudentScore(student);
        }
    }

    private void increaseStudentScore(Student student) {
        studentDao.increaseStudentScore(student);
    }
}
