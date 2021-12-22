package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class Question {
    private final String question;
    private final List<AnswerVariant> variants;

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
