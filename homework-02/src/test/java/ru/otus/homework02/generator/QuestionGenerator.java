package ru.otus.homework02.generator;

import ru.otus.homework02.domain.Question;

import java.util.HashMap;
import java.util.List;

public class QuestionGenerator {

    public static Question generateQuestion() {
        HashMap<String, List<String>> questionMap = new HashMap<>();
        questionMap.put("How many continents are there on earth?", List.of("6","7","8"));
        questionMap.put("What is the square root of 4?", List.of("3","2","1"));
        questionMap.put("How many people in 1 soccer team?", List.of("10","13","11"));
        questionMap.put("How many days in a leap year?", List.of("366","365","364"));
        questionMap.put("Who created the java language?", List.of("Gosling","Gates","Putin"));
        return new Question(questionMap);
    }
}
