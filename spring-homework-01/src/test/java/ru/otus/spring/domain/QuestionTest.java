package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class Question")
class QuestionTest {

    @DisplayName("get question success")
    @Test
    void getQuestion() {
        var question = new Question("question 1", List.of("1", "2"));
        assertEquals("question 1", question.getQuestion());
    }

    @DisplayName("get answers success")
    @Test
    void getAnswersVarians() {
        var question = new Question("question 1", List.of("1", "2"));
        assertEquals(List.of("1", "2"), question.getAnswersVarians());
    }
}