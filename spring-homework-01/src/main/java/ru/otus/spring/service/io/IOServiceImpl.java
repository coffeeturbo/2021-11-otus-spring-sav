package ru.otus.spring.service.io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.exception.InputVariantMismatchException;

import java.io.PrintStream;

@Service
public class IOServiceImpl implements IOService {

    private final Input scanner;
    private final PrintStream printStream;

    public IOServiceImpl(
            Input input,
            @Value("#{ T(java.lang.System).out }") PrintStream out
    ) {
        this.scanner = input;
        this.printStream = out;
    }

    @Override
    public int askInt(String question, int max) throws InputVariantMismatchException {
        int select;
        try {
            select = askInt(question);
            if (select < 1 || select > max) {
                throw new InputVariantMismatchException(
                        String.format("The inputted answer variant: %s doesn't exist", select)
                );
            }
        } catch (NumberFormatException e) {
            throw new InputVariantMismatchException("The inputted variant is not numeric");
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
