package ru.otus.spring.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Quiz {
    private int rightAnsweredCount;
    private int badAnsweredCount;

    private User user;

    public Quiz(User user) {
        this.user = user;
    }

    public void incrementRightAnsweredCount() {
        rightAnsweredCount++;
    }

    public void incrementRightAnsweredCountIfNecessary(boolean incr) {
        if (incr) {
            incrementRightAnsweredCount();
        } else {
            incrementBadAnsweredCount();
        }
    }

    public void incrementBadAnsweredCount() {
        badAnsweredCount++;
    }
}
