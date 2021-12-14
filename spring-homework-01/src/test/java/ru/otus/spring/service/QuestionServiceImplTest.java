package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.AnswerVariant;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.QuestionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao dao;

    @Test
    void whenGetQuestionsSuccess() throws QuestionException {
//        List<String[]> list = new ArrayList<>(List.of());
//        list.add(new String[] {"Question ?", "1"});

        given(dao.getQuestions())
                .willReturn(List.of(
                        new Question("Question ?", List.of(new AnswerVariant("1", false)))
                ));

        var questionService = new QuestionServiceImpl(dao);

        var question = new Question("Question ?", List.of(new AnswerVariant("1", false)));
        Assertions.assertThat(questionService.getQuestions())
                .hasSize(1)
                .contains(question);
    }

    @Test
    void whenGetQuestionsException() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[] {"Question ?", "1"});

        given(dao.getQuestions())
                .willReturn(Collections.emptyList());

        var questionService = new QuestionServiceImpl(dao);

        Exception exception = assertThrows(QuestionException.class, questionService::getQuestions);

        Assertions.assertThat(exception.getMessage())
                .isEqualTo("bad file format error");
    }
}