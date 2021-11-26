package service;

import dao.QuestionDao;
import domain.Question;
import exception.QuestionsNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    @Override
    public List<String> getQuestions() throws QuestionsNotFoundException {
        return dao.findAllQuestions()
                .stream()
                .map(Question::getQuestionWithVariants)
                .collect(Collectors.toList());
    }
}
