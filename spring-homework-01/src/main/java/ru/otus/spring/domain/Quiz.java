package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
