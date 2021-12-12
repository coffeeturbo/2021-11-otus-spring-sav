package ru.otus.spring.service.io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class OutputImpl implements Output {

    private final PrintStream printStream;

    public OutputImpl(@Value("#{ T(java.lang.System).in }") PrintStream stream) {
        this.printStream = stream;
    }

    @Override
    public void println(String text) {
        printStream.println(text);
    }

}
