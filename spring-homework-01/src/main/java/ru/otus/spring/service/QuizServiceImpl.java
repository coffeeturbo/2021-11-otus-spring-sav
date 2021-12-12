package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.formatter.QuestionFormatter;
import ru.otus.spring.domain.formatter.UserFormatter;
import ru.otus.spring.exception.QuestionsBadFormatException;
import ru.otus.spring.service.io.Input;
import ru.otus.spring.service.io.Output;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;
    private final Output output;
    private final Input input;
    private final int successPercent;
    private final Quiz quiz = new Quiz();


    public QuizServiceImpl(QuestionService questionService,
                           Output output,
                           Input input,
                           @Value("${app.success-percent}") int successPercent) {
        this.questionService = questionService;
        this.output = output;
        this.input = input;
        this.successPercent = successPercent;
    }

    @Override
    public void startQuiz() {
        askUserInfo();
        askUserQuestions();
        printQuizResults();
    }

    private String askUserName() {
        return input.askStr("Input your Name: ");
    }

    private String askUserSurnName() {
        return input.askStr("Input your Surname: ");
    }

    private void askUserQuestions() {
        try {
            for (var question : questionService.getQuestions()) {
                var formatter = new QuestionFormatter(question);

                var run = true;
                while (run) {
                    try {
                        var answer = input.askInt(formatter.fullQuestion(), question.getVariants().size());
                        if (answer == question.getRightAnswerVariantIndex()) {
                            quiz.incrementRightAnsweredCount();
                            output.println("right");
                        } else {
                            quiz.incrementBadAnsweredCount();
                            output.println("bad answer");
                        }

                        run = false;
                    } catch (IllegalStateException e) {
                        run = true;
                        output.println("введенный варинт ответа отсутствует");
                        output.println("Попробуйте еще раз");
                    }

                }
            }
        } catch (QuestionsBadFormatException e) {
            e.printStackTrace();
        }
    }

    private void printQuizResults() {

        var userFormatter = new UserFormatter(quiz.getUser());
        output.println(userFormatter.format());
        output.println("right answered: " + quiz.getRightAnsweredCount());
        output.println("Bad answered: " + quiz.getBadAnsweredCount());

        var x = 100 * quiz.getRightAnsweredCount() / (quiz.getRightAnsweredCount() + quiz.getBadAnsweredCount());

        if (x >= successPercent) {
            output.println("Exam passed");
        } else {
            output.println("Exam failed");
        }
    }

    private void askUserInfo() {
        var name = askUserName();
        var surname = askUserSurnName();
        quiz.setUser(new User(name, surname));
    }
}
