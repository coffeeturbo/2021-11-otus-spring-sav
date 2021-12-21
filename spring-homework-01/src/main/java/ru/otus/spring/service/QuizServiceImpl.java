package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
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

import java.util.Locale;

@Slf4j
@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;
    private final IOService ioService;
    private final int successPercent;
    private final UserFormatter userFormatter;
    private final QuestionFormatter questionFormatter;
    private final MessageSource message;
    private final Locale locale;


    public QuizServiceImpl(QuestionService questionService,
                           IOService ioService,
                           AppConfig config,
                           UserFormatter userFormatter,
                           QuestionFormatter questionFormatter,
                           MessageSource messageSource
    ) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.successPercent = config.getSuccessPercent();
        this.userFormatter = userFormatter;
        this.questionFormatter = questionFormatter;
        this.message = messageSource;
        this.locale = config.getLocale();
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
        var rightAnswerMess = getMessage("strings.app.answer.right");
        var badAnswerMess = getMessage("strings.app.answer.bad");
        var tryAgainMess = getMessage("strings.app.answer.try");

        var stringQuestion = questionFormatter.fullQuestion(question);
        boolean rightAnswer = false;
        while (true) {
            try {
                var answer = ioService.askInt(stringQuestion, question.getVariants().size());
                if (answer == question.getRightAnswerVariantIndex()) {
                    rightAnswer = true;
                    ioService.println(rightAnswerMess);
                } else {
                    ioService.println(badAnswerMess);
                }
                break;
            } catch (InputVariantMismatchException e) {
                log.warn(e.getMessage());
                log.warn(tryAgainMess);
            }
        }
        return rightAnswer;
    }

    private void printQuizResults(Quiz quiz) {
        var answeredMes = getMessage("strings.app.result.answered");
        var answeredBadMes = getMessage("strings.app.result.answered.bad");

        ioService.println(userFormatter.format(quiz.getUser()));
        ioService.println(answeredMes + ": " + quiz.getRightAnsweredCount());
        ioService.println(answeredBadMes + ": " + quiz.getBadAnsweredCount());

        var x = 100 * quiz.getRightAnsweredCount() / (quiz.getRightAnsweredCount() + quiz.getBadAnsweredCount());

        if (x >= successPercent) {
            ioService.println(getMessage("strings.app.result.quiz.passed"));
        } else {
            ioService.println(getMessage("strings.app.result.quiz.failed"));
        }
    }

    private User askUserInfo() {
        var askName = getMessage("strings.app.input.name");
        var askSurname = getMessage("strings.app.input.surname");

        var name = ioService.askStr(askName + ": ");
        var surname = ioService.askStr(askSurname + ": ");
        return new User(name, surname);
    }

    private String getMessage(String code) {
        return message.getMessage(code, null, locale);
    }
}
