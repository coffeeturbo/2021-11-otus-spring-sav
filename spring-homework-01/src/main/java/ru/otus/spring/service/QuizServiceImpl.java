package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Quiz;
import ru.otus.spring.domain.User;
import ru.otus.spring.exception.InputVariantMismatchException;
import ru.otus.spring.exception.QuestionException;
import ru.otus.spring.formatter.QuestionFormatter;
import ru.otus.spring.formatter.UserFormatter;
import ru.otus.spring.service.io.IOService;

@Slf4j
@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;
    private final IOService ioService;
    private final int successPercent;
    private final UserFormatter userFormatter;
    private final QuestionFormatter questionFormatter;
    private final LocalizationService messageService;


    public QuizServiceImpl(QuestionService questionService,
                           IOService ioService,
                           AppConfig config,
                           UserFormatter userFormatter,
                           QuestionFormatter questionFormatter,
                           LocalizationService messageService
    ) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.successPercent = config.getSuccessPercent();
        this.userFormatter = userFormatter;
        this.questionFormatter = questionFormatter;
        this.messageService = messageService;
    }

    @Override
    public void startQuiz() {
        User user = askUserInfo();
        Quiz quizResult = askUserQuestions(user);
        printQuizResults(quizResult);
    }

    private Quiz askUserQuestions(User user) {
        var quiz = new Quiz(user);
        try {
            for (var question : questionService.getQuestions()) {
                var res = askQuestion(question);
                quiz.incrementRightAnsweredCountIfNecessary(res);
            }
        } catch (QuestionException e) {
            log.error(e.getMessage());
        }
        return quiz;
    }

    private boolean askQuestion(Question question) {
        var stringQuestion = questionFormatter.fullQuestion(question);
        boolean rightAnswer = false;
        while (true) {
            try {
                var answer = ioService.askInt(stringQuestion, question.getVariants().size());
                if (answer == question.getRightAnswerVariantIndex()) {
                    rightAnswer = true;
                    ioService.println(messageService.getMessage("strings.app.answer.right"));
                } else {
                    ioService.println(messageService.getMessage("strings.app.answer.bad"));
                }
                break;
            } catch (InputVariantMismatchException e) {
                log.warn(e.getMessage());
                log.warn(messageService.getMessage("strings.app.answer.try"));
            }
        }
        return rightAnswer;
    }

    private void printQuizResults(Quiz quiz) {
        var answeredMes = messageService.getMessage("strings.app.result.answered");
        var answeredBadMes = messageService.getMessage("strings.app.result.answered.bad");

        ioService.println(userFormatter.format(quiz.getUser()));
        ioService.println(answeredMes + ": " + quiz.getRightAnsweredCount());
        ioService.println(answeredBadMes + ": " + quiz.getBadAnsweredCount());

        var x = 100 * quiz.getRightAnsweredCount() / (quiz.getRightAnsweredCount() + quiz.getBadAnsweredCount());

        if (x >= successPercent) {
            ioService.println(messageService.getMessage("strings.app.result.quiz.passed"));
        } else {
            ioService.println(messageService.getMessage("strings.app.result.quiz.failed"));
        }
    }

    private User askUserInfo() {
        var askName = messageService.getMessage("strings.app.input.name");
        var askSurname = messageService.getMessage("strings.app.input.surname");

        var name = ioService.askStr(askName + ": ");
        var surname = ioService.askStr(askSurname + ": ");
        return new User(name, surname);
    }
}
