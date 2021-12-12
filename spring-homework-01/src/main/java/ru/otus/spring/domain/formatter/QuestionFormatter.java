package ru.otus.spring.domain.formatter;

import lombok.Value;
import ru.otus.spring.domain.Question;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Value
public class QuestionFormatter {

    Question question;

    public String fullQuestion() {
        AtomicInteger index = new AtomicInteger();

        var variants = question.getVariants().stream()
                .map(s -> "Variant [" + index.incrementAndGet() + "]: " + s.getTextVariant())
                .collect(Collectors.toList());

        return question.getQuestion() + System.lineSeparator() + String.join(System.lineSeparator(), variants) + System.lineSeparator();
    }
}
