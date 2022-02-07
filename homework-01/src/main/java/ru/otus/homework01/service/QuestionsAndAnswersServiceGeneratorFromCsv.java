package ru.otus.homework01.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.homework01.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@Data
@RequiredArgsConstructor
public class QuestionsAndAnswersServiceGeneratorFromCsv implements QuestionsAndAnswersServiceGenerator {
    private static final String SPLITTER = ";";
    private final String resource;

    @Override
    public void generateQuestionsAndAnswers() {
        String line;
        InputStream is = Main.class.getResourceAsStream(resource);
        try (InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(is))) {
            try (BufferedReader br = new BufferedReader(streamReader)) {
                while ((line = br.readLine()) != null) {
                    String[] qAndA = line.split(SPLITTER);
                    for (String s : qAndA) {
                        System.out.println(s);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }
}
