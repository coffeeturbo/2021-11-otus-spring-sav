package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Quiz {
    private int rightAnsweredCount;
    private int badAnsweredCount;

    private User user;

    public void incrementRightAnsweredCount() {
        rightAnsweredCount++;
    }

    public void incrementBadAnsweredCount() {
        badAnsweredCount++;
    }
}
