package ru.otus.homework02.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource("classpath:application.properties")
public class QuestionsAndAnswersConfig {

    @Value("${questions-and-answers}")
    private String questionsAndAnswers;

    @Value("${right-answers}")
    private String rightAnswers;
}
