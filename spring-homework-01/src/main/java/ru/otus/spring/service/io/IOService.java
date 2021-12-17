package ru.otus.spring.service.io;

import ru.otus.spring.exception.InputVariantMismatchException;

public interface IOService {
    int askInt(String question, int max) throws InputVariantMismatchException;
    String askStr(String str);
    int askInt(String str) throws NumberFormatException;
    void println(String text);
}
