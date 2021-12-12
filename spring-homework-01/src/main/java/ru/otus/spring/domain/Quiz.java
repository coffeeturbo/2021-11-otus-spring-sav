package ru.otus.spring.domain;

import lombok.Data;

@Data
public class Quiz {
    int rightAnsweredCount;
    int badAnsweredCount;

    User user;

    public void incrementRightAnsweredCount() {
        rightAnsweredCount++;
    }

    public void incrementBadAnsweredCount() {
        badAnsweredCount++;
    }

}
