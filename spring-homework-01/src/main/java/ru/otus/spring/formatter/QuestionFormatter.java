package ru.otus.spring.formatter;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.AnswerVariant;
import ru.otus.spring.domain.Question;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class QuestionFormatter {

    public String fullQuestion(final Question question) {
        AtomicInteger index = new AtomicInteger();

        var variants = question.getVariants().stream()
                .map(s -> "Variant [" + index.incrementAndGet() + "]: " + s.getTextVariant())
                .collect(Collectors.toList());

        return question.getQuestion() + System.lineSeparator() + String.join(System.lineSeparator(), variants) + System.lineSeparator();
    }

    public Question parseFromStringArray(String[] quest) {
        var variants = Arrays.stream(quest)
                .filter(s -> !s.contains("?"))
                .map(s -> new AnswerVariant(s.replace("*", ""), s.contains("*")))
                .collect(Collectors.toList());

        var question = Arrays.stream(quest)
                .filter(s -> s.contains("?"))
                .findFirst()
                .orElse(null);

        return new Question(question, variants);
    }
}
