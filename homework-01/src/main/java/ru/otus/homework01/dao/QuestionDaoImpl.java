package ru.otus.homework01.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.homework01.Main;
import ru.otus.homework01.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {
    private final String resource;
    private static final String SPLITTER = ";";

    @Override
    public Question receiveQuestion() {
        String line;
        InputStream is = Main.class.getResourceAsStream(resource);
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

