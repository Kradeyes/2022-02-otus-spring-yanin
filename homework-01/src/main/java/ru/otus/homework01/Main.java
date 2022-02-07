package ru.otus.homework01;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework01.domain.Question;
import ru.otus.homework01.service.QuestionService;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/application-context.xml");
        QuestionService generator = context.getBean(QuestionService.class);
        Question question = generator.generateQuestion();
        for (String s : question.getQuestionsWithAnswers().keySet()) {
            System.out.println(s);
            System.out.println(question.getQuestionsWithAnswers().get(s));
        }
        context.close();
    }
}
