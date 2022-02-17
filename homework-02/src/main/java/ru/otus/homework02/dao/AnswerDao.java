package ru.otus.homework02.dao;

import ru.otus.homework02.domain.Answer;

public interface AnswerDao {
    Answer receiveAnswer(String selectedLocale);
}
