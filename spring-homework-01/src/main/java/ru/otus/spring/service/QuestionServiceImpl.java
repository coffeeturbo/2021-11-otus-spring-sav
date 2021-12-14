package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.QuestionException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    @Override
    public List<Question> getQuestions() throws QuestionException {
        var data = dao.getQuestions();

        if (data == null || data.isEmpty()) {
            throw new QuestionException("bad file format error");
        }

        return data;
    }

}
