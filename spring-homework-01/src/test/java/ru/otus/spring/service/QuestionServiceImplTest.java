package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.AnswerVariant;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.QuestionsBadFormatException;
import ru.otus.spring.service.io.Output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao dao;

    @Mock
    private Output output;

    @Mock
    private CsvResourceLoader loader;

    @Test
    void whenGetQuestionsSuccess() throws QuestionsBadFormatException {
        List<String[]> list = new ArrayList<>(List.of());
        list.add(new String[] {"Question ?", "1"});

        given(dao.getCsvDaoFilename())
                .willReturn("test.csv");

        given(loader.readData(anyString()))
                .willReturn(list);

        var questionService = new QuestionServiceImpl(dao, loader);

        var question = new Question("Question ?", List.of(new AnswerVariant("1", false)));
        Assertions.assertThat(questionService.getQuestions())
                .hasSize(1)
                .contains(question);
    }

    @Test
    void whenGetQuestionsException() {
        List<String[]> list = new ArrayList<>();
        list.add(new String[] {"Question ?", "1"});

        given(dao.getCsvDaoFilename())
                .willReturn("anyString");

        given(loader.readData("anyString"))
                .willReturn(Collections.emptyList());

        var questionService = new QuestionServiceImpl(dao, loader);

        Exception exception = assertThrows(QuestionsBadFormatException.class, questionService::getQuestions);

        Assertions.assertThat(exception.getMessage())
                .isEqualTo("Неправильынй формат фала");
    }
}