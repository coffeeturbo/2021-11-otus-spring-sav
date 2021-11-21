package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceImplTest {

    @Test
    void whenGetQuestionsSuccess() {
        QuestionDao dao = Mockito.mock(QuestionDao.class);

        Mockito.when(dao.findAllQuestions()).thenReturn(List.of(new Question("question 1?", List.of())));
        var service = new QuestionServiceImpl(dao);
        assertEquals(1, service.getQuestions().size());
        assertEquals("question 1?", service.getQuestions().get(0));
    }
}