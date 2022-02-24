package ru.otus.homework02.dao;

import ru.otus.homework02.domain.Question;

public interface QuestionDao {
    Question receiveQuestion(String selectedLocale);
}
