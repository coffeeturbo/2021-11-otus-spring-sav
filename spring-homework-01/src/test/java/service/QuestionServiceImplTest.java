package service;

import dao.QuestionDao;
import domain.Question;
import exception.QuestionsNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao dao;

    @Test
    void whenGetQuestionsSuccess() throws QuestionsNotFoundException {
        given(dao.findAllQuestions())
                .willReturn(List.of(new Question(List.of("question 1?"))));

        var service = new QuestionServiceImpl(dao);
        assertEquals(1, service.getQuestions().size());
        assertEquals("question 1?", service.getQuestions().get(0));
    }
}