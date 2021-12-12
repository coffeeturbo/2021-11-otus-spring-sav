package ru.otus.spring.service.io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Scanner;

@Component
public class ConsoleInput implements Input {

    private final Scanner scanner;

    public ConsoleInput(@Value("#{ T(java.lang.System).in }") InputStream input) {
        this.scanner = new Scanner(input);
    }

    @Override
    public int askInt(String question, int max) throws IllegalStateException  {
        int select;
        try {
            select  = askInt(question);
        } catch (NumberFormatException e) {
            throw new IllegalStateException(e);
        }

        if (select < 1 || select > max) {
            throw new IllegalStateException(String.format("Out of about %s > [0, %s]", select, max));
        }

        return select;
    }

    @Override
    public String askStr(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    @Override
    public int askInt(String question) throws NumberFormatException {
        return Integer.parseInt(askStr(question));
    }
}
