package ru.otus.spring.exception;

public class QuestionsBadFormatException extends Exception {
    public QuestionsBadFormatException(String message) {
        super(message);
    }
}
