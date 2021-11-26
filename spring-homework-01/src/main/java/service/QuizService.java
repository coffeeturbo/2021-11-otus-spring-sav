package service;

import exception.QuestionsNotFoundException;

public interface QuizService {
    void printAllQuestions() throws QuestionsNotFoundException;
    void startQuiz();
}
