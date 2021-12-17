package ru.otus.spring.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class QuizTest {

    Quiz quiz;

    @BeforeEach
    void init() {
        quiz = new Quiz();
    }

    @Test
    void incrementRightAnsweredCount() {
        quiz.incrementRightAnsweredCount();
        assertThat(quiz.getRightAnsweredCount()).isEqualTo(1);
    }

    @Test
    void incrementBadAnsweredCount() {
        quiz.incrementBadAnsweredCount();
        assertThat(quiz.getBadAnsweredCount()).isEqualTo(1);
    }

    @Test
    void getRightAnsweredCount() {
        assertThat(quiz.getRightAnsweredCount()).isEqualTo(0);
    }

    @Test
    void getBadAnsweredCount() {
        assertThat(quiz.getBadAnsweredCount()).isEqualTo(0);
    }

    @Test
    void getUser() {
        quiz.setUser(new User("test", "test"));
        assertThat(quiz.getUser()).isEqualTo(new User("test", "test"));
    }
    @Test
    void getUserNotEquals() {
        quiz.setUser(new User("test2", "test2"));
        assertThat(quiz.getUser()).isNotEqualTo(new User("test", "test"));
    }
}