package ru.otus.homework02.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class QuestionsAndAnswersConfig {

    private String questionsAndAnswers;

    private String rightAnswers;

    private String questionsAndAnswersRu;

    private String rightAnswersRu;

    private String questionsAndAnswersFr;

    private String rightAnswersFr;
}
