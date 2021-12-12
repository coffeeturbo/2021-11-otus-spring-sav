package ru.otus.spring.service.io;

public interface Input {
    int askInt(String question, int max) throws IllegalStateException;

    String askStr(String question);

    int askInt(String question);
}
