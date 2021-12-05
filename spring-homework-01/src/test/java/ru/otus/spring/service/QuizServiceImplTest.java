package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.AnswerVariant;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.QuestionsBadFormatException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    QuestionService questionService;

    @Mock
    IOService ioService;


    @Test
    void printAllQuestions() throws QuestionsBadFormatException {

        var quizService = new QuizServiceImpl(questionService, ioService);

        given(questionService.getQuestions())
                .willReturn(List.of(
                        new Question("1?", List.of(new AnswerVariant("ansswer", true))),
                        new Question("2?", List.of(new AnswerVariant("ansswer", true))),
                        new Question("3?", List.of(new AnswerVariant("ansswer", true)))
                ));
        assertEquals(3, questionService.getQuestions().size());

        doNothing()
                .when(ioService)
                .println(any(Question.class));

        ioService.println(new Question("1?", List.of(new AnswerVariant("ansswer", true))));
        verify(ioService, times(1)).println(any(Question.class));
        assertEquals(3, questionService.getQuestions().size());
    }

    @Test
    void startQuiz() {

    }

    @Test
    void testStartQuiz() {
    }
}