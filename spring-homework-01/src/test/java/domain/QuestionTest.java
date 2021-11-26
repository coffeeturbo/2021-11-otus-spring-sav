package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class Question")
class QuestionTest {

    @DisplayName("get all question with variants success")
    @Test
    void getQuestionWithVariants() {
        var question = new Question(List.of("question 1", "1", "2"));
        assertEquals("question 1 1 2", question.getQuestionWithVariants());
    }

    @DisplayName("get question success")
    @Test
    void getQuestion() {
        var question = new Question(List.of("question 1", "1", "2"));
        assertEquals("question 1", question.getQuestionText());
    }

    @DisplayName("get answers variants text success")
    @Test
    void getAnswersVariansTextSuccess() {
        var question = new Question(List.of("question 1", "1", "2"));
        assertEquals("1 2", question.getAnswerVariantsText());
    }
}