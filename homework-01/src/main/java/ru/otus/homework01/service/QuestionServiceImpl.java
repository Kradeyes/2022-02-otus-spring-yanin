package ru.otus.homework01.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.otus.homework01.dao.QuestionDao;
import ru.otus.homework01.domain.Question;

@Data
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;

    @Override
    public Question generateQuestion() {
        return dao.receiveQuestion();
    }
}
