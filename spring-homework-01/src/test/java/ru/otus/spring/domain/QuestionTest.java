package ru.otus.spring.domain;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuestionTest {

    @Test
    void getQuestion() {
        var question = new Question("Who was neo?", List.of(new AnswerVariant("chosen one", true)));

        Assertions.assertThat(question.getQuestion())
                .isEqualTo("Who was neo?")
                .contains("?");
    }

    @Test
    void getVariants() {
        var question = new Question("Who was neo?", List.of(
                new AnswerVariant("chosen one", true),
                new AnswerVariant("chosen two", false),
                new AnswerVariant("chosen three", false)
        ));

        Assertions.assertThat(question.getVariants())
                .hasSize(3)
                .extracting("textVariant", "isRight")
                .containsAnyOf(Tuple.tuple("chosen one", true));
    }
}