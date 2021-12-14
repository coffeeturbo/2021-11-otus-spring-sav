package ru.otus.spring.exception;

import java.util.InputMismatchException;

public class InputVariantMismatchException extends InputMismatchException {
    public InputVariantMismatchException(String s) {
        super(s);
    }
}
