package dao;

import domain.Question;
import exception.QuestionsNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import service.IOServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionDaoCSVTest {

    @Test
    public void whenSuccess() throws QuestionsNotFoundException {
        QuestionDao dao = new QuestionDaoCSV("test-questions.csv", new IOServiceImpl());

        var quiestions = dao.findAllQuestions();

        assertThat(quiestions)
                .hasOnlyElementsOfType(Question.class)
                .hasSize(5)
                .extracting(Question::getQuestion)
                .contains(List.of("Как зовут главного героя фильма матрица?", "Нео", "Картман", "Пол Андерсен"));
    }

    @Test
    public void whenFileEmpty() {

        var exception = Assertions.catchThrowableOfType(() -> {
            QuestionDao dao = new QuestionDaoCSV("test-empty-questions.csv", new IOServiceImpl());
            dao.findAllQuestions();
        }, QuestionsNotFoundException.class);

        assertThat(exception.getMessage())
                .contains("В файле не найдено вопросов");
    }

//    @Test
//    public void whenFileNotFoundIOexception() {
//        var exception = Assertions.catchThrowableOfType(() -> {
//            QuestionDao dao = new QuestionDaoCSV("not-existed.csv", new IOServiceImpl());
//            dao.findAllQuestions();
//        }, IOException.class);
//
//        assertThat(exception.getMessage())
//                .contains("В файле не найдено вопросов");
//    }
}