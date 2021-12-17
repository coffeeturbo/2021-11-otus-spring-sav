package ru.otus.spring.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerVariantTest {

    @Test
    void whenGetTextVariantSuccess() {
        var variant = new AnswerVariant("Neo", true);
        assertEquals("Neo", variant.getTextVariant());
    }

    @Test
    void whenIsRightTrue() {
        var variant = new AnswerVariant("Neo", true);
        assertTrue(variant.isRight());
    }

    @Test
    void whenIsRightFalse() {
        var variant = new AnswerVariant("Neo", false);
        assertFalse(variant.isRight());
    }
}