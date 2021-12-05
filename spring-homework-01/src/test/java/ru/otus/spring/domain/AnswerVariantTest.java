package ru.otus.spring.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerVariantTest {

    @Test
    void getTextVariant() {
        var variant = new AnswerVariant("Neo", true);
        assertEquals("Neo", variant.getTextVariant());

    }

    @Test
    void isRightTrue() {
        var variant = new AnswerVariant("Neo", true);
        assertTrue(variant.isRight());
    }

    @Test
    void isRightFalse() {
        var variant = new AnswerVariant("Neo", false);
        assertFalse(variant.isRight());
    }
}