package ru.otus.spring.domain.formatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.AnswerVariant;
import ru.otus.spring.domain.Question;

import java.util.List;

class QuestionFormatterTest {

    @Test
    void fullQuestion() {

        Question question = new Question("What is yoor age", List.of(
                new AnswerVariant("variant 1", false),
                new AnswerVariant("variant 2", false),
                new AnswerVariant("variant 3", true)
        ));

        QuestionFormatter questionFormatter = new QuestionFormatter();

        Assertions.assertThat(questionFormatter.fullQuestion(question)).isEqualTo(
                "What is yoor age" + System.lineSeparator()
                        + "Variant [1]: variant 1" + System.lineSeparator()
                        + "Variant [2]: variant 2" + System.lineSeparator()
                        + "Variant [3]: variant 3" + System.lineSeparator());
    }
}