package ru.otus.spring.service.io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.exception.InputVariantMismatchException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {

    private final Scanner scanner;
    private final PrintStream printStream;

    public IOServiceImpl(
            @Value("#{ T(java.lang.System).in }") InputStream input,
            @Value("#{ T(java.lang.System).out }") PrintStream out
    ) {
        this.scanner = new Scanner(input);
        this.printStream = out;
    }

    @Override
    public int askInt(String question, int max) throws InputVariantMismatchException {
        int select;
        try {
            select = askInt(question);
            if (select < 1 || select > max) {
                throw new InputVariantMismatchException(
                        String.format("the inputted answer variant: %s doesn't exist", select));
            }
        } catch (NumberFormatException e) {
            throw new InputVariantMismatchException("the inputted answer is not numeric");
        }

        return select;
    }

    @Override
    public String askStr(String str) {
        printStream.print(str);
        return scanner.nextLine();
    }

    @Override
    public int askInt(String str) throws NumberFormatException {
        return Integer.parseInt(askStr(str));
    }

    @Override
    public void println(String text) {
        printStream.println(text);
    }

}
