package service;

import exception.QuestionsNotFoundException;

import java.util.List;

public interface QuestionService {
    List<String> getQuestions() throws QuestionsNotFoundException;
}
