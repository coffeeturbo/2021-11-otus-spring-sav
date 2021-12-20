package ru.otus.spring.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AnswerVariant {
    private final String textVariant;
    private final boolean isRight;
}
