package service;

import exception.QuestionsNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;
    private final IOServiceImpl ioService;


    @Override
    public void printAllQuestions() {
        try {
            questionService.getQuestions().forEach(ioService::println);
        } catch (QuestionsNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startQuiz() {
        printAllQuestions();
    }
}
