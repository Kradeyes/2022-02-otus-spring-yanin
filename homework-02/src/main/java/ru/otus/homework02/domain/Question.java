package ru.otus.homework02.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class Question {
    private final Map<String, List<String>> questionsWithAnswers;
}
