package ru.otus.homework02.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework02.Main;
import ru.otus.homework02.config.QuestionsAndAnswersConfig;
import ru.otus.homework02.domain.Answer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AnswerDaoImpl implements AnswerDao {

    private static final String SPLITTER = ";";
    private final QuestionsAndAnswersConfig config;

    @Override
    public Answer receiveAnswer() {
        String line;
        String[] answersFromCsv = new String[0];
        InputStream is = Main.class.getResourceAsStream(config.getRightAnswers());
        try (InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(is))) {
            try (BufferedReader br = new BufferedReader(streamReader)) {
                while ((line = br.readLine()) != null) {
                    answersFromCsv = line.split(SPLITTER);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        return new Answer(answersFromCsv);
    }
}
