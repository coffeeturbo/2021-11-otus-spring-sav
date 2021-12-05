package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.exception.QuestionsBadFormatException;

@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;
    private final IOService ioService;


    protected void printAllQuestions() {
        try {
            questionService.getQuestions().forEach(ioService::println);
        } catch (QuestionsBadFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startQuiz() {
        printAllQuestions();
    }
}
