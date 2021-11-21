package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionDao;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    @Override
    public List<String> getQuestions() {

        List<String> quiestions = new ArrayList<>();
        dao.findAllQuestions()
                .forEach(question -> quiestions.add(question.getQuestion()));

        return quiestions;
    }
}
