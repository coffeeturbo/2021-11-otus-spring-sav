package ru.otus.spring.service.io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Scanner;

@Component
public class InputProvider implements Input {

    private final InputStream input;

    public InputProvider(@Value("#{ T(java.lang.System).in }") InputStream input) {
        this.input = input;
    }

    @Override
    public String nextLine() {
        return new Scanner(input).nextLine();
    }

}
