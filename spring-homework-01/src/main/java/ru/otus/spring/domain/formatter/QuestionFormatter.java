package ru.otus.spring.domain.formatter;

import lombok.Value;
import ru.otus.spring.domain.AnswerVariant;
import ru.otus.spring.domain.Question;

import java.util.stream.Collectors;

@Value
public class QuestionFormatter {

    Question question;

    public String fullQuestion() {
        var variants = question.getVariants().stream()
                .map(AnswerVariant::getTextVariant)
                .collect(Collectors.toList());

        return question.getQuestion() + " " + String.join(" ", variants);
    }
}
