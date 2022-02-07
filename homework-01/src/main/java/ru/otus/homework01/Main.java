package ru.otus.homework01;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework01.service.QuestionsAndAnswersServiceGenerator;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");
        QuestionsAndAnswersServiceGenerator generator = context.getBean("questionsAndAnswersFromCsv", QuestionsAndAnswersServiceGenerator.class);
        generator.generateQuestionsAndAnswers();
        context.close();
    }

}
