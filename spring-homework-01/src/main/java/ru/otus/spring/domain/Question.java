package ru.otus.spring.domain;

import lombok.Value;

import java.util.List;

@Value
public class Question {
    String question;
    List<AnswerVariant> variants;

    public int getRightAnswerVariantIndex() {
        var it = variants.iterator();
        for (int i = 1; i <= variants.size(); i++) {
            var question = it.next();
            if (question.isRight()) {
                return i;
            }
        }
        return 0;
    }
}
