package ru.otus.homework01.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QuestionsAndAnswersServiceGeneratorFromCsvTest {
    private static ClassPathXmlApplicationContext context;
    private static QuestionsAndAnswersServiceGenerator service;

    @BeforeClass
    public static void setUpBeforeClass() {
        context = new ClassPathXmlApplicationContext("application-context.xml");
        service = (QuestionsAndAnswersServiceGeneratorFromCsv) context.getBean("questionsAndAnswersFromCsv");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        context.close();
    }

    @Test
    public void generateQuestionsAndAnswersTest() {
        service.generateQuestionsAndAnswers();
    }
}