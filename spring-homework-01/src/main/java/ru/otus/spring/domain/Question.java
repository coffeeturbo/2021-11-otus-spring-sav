package ru.otus.spring.domain;

import lombok.Value;

import java.util.List;

@Value
public class Question {
    String question;
    List<AnswerVariant> variants;
}
