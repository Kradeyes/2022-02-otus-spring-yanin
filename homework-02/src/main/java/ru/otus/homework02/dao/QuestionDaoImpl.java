package ru.otus.homework02.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework02.Main;
import ru.otus.homework02.config.QuestionsAndAnswersConfig;
import ru.otus.homework02.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private static final String SPLITTER = ";";
    private final QuestionsAndAnswersConfig config;

    @Override
    public Question receiveQuestion() {
        String line;
        InputStream is = Main.class.getResourceAsStream(config.getQuestionsAndAnswers());
        Map<String, List<String>> questionMap = new HashMap<>();
        try (InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(is))) {
            try (BufferedReader br = new BufferedReader(streamReader)) {
                while ((line = br.readLine()) != null) {
                    String[] qAndA = line.split(SPLITTER);
                    questionMap.put(getQuestion(qAndA), getAnswers(qAndA));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        return new Question(questionMap);
    }

    private String getQuestion(String[] stringsFromCsv) {
        String question = "";
        if (Arrays.stream(stringsFromCsv).findFirst().isPresent()) {
            question = Arrays.stream(stringsFromCsv).findFirst().get();
        }
        return question;
    }

    private List<String> getAnswers(String[] stringsFromCsv) {
        return Arrays.stream(stringsFromCsv).skip(1).collect(Collectors.toList());
    }
}

