package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.formatter.QuestionFormatter;
import ru.otus.spring.domain.formatter.UserFormatter;
import ru.otus.spring.exception.InputVariantMismatchException;
import ru.otus.spring.exception.QuestionException;
import ru.otus.spring.service.io.IOService;

@Slf4j
@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;
    private final IOService ioService;
    private final int successPercent;
    private final UserFormatter userFormatter;
    private final QuestionFormatter questionFormatter;
    private final Quiz quiz = new Quiz();


    public QuizServiceImpl(QuestionService questionService,
                           IOService ioService,
                           @Value("${app.success-percent}") int successPercent,
                           UserFormatter userFormatter,
                           QuestionFormatter questionFormatter

    ) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.successPercent = successPercent;
        this.userFormatter = userFormatter;
        this.questionFormatter = questionFormatter;
    }

    @Override
    public void startQuiz() {
        askUserInfo();
        askUserQuestions();
        printQuizResults();
    }

    private void askUserQuestions() {
        try {
            for (var question : questionService.getQuestions()) {
                askQuestion(question);
            }
        } catch (QuestionException e) {
            log.error(e.getMessage());
        }
    }

    private void askQuestion(Question question) {
        var stringQuestion = questionFormatter.fullQuestion(question);
        while (true) {
            try {
                var answer = ioService.askInt(stringQuestion, question.getVariants().size());
                if (answer == question.getRightAnswerVariantIndex()) {
                    quiz.incrementRightAnsweredCount();
                    ioService.println("right");
                } else {
                    quiz.incrementBadAnsweredCount();
                    ioService.println("bad answer");
                }
                break;
            } catch (InputVariantMismatchException e) {
                log.warn(e.getMessage());
                log.warn("Try again please");
            }
        }
    }

    private void printQuizResults() {

        ioService.println(userFormatter.format(quiz.getUser()));
        ioService.println("Right answered: " + quiz.getRightAnsweredCount());
        ioService.println("Bad answered: " + quiz.getBadAnsweredCount());

        var x = 100 * quiz.getRightAnsweredCount() / (quiz.getRightAnsweredCount() + quiz.getBadAnsweredCount());

        if (x >= successPercent) {
            ioService.println("Exam passed");
        } else {
            ioService.println("Exam failed");
        }
    }

    private void askUserInfo() {
        var name = ioService.askStr("Input your Name: ");
        var surname = ioService.askStr("Input your Surname: ");
        quiz.setUser(new User(name, surname));
    }
}
