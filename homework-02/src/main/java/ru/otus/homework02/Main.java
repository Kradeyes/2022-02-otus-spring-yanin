package ru.otus.homework02;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.Student;
import ru.otus.homework02.service.StudentTestService;

import java.util.Scanner;


@ConfigurationPropertiesScan
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        StudentTestService studentTestService = context.getBean(StudentTestService.class);
        Question question = studentTestService.generateQuestion();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = in.next();
        System.out.println("Enter your surname");
        String surname = in.next();
        Student student = studentTestService.setStudentNameAndSurname(name, surname);
        for (String s : question.getQuestionsWithAnswers().keySet()) {
            System.out.println(s);
            System.out.println(question.getQuestionsWithAnswers().get(s));
            String answer = in.next();
            studentTestService.checkStudentAnswer(answer, student);
        }
        System.out.println(student.getSurname() + " "
                + student.getName() + " the number of points you have earned = "
                + studentTestService.getStudentScore(student));
        in.close();
        context.close();
    }
}
