package ru.otus.spring.service;

import lombok.Setter;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.formatter.QuestionFormatter;

import java.io.PrintStream;

@Setter
public class IOServiceImpl implements IOService {

    private PrintStream printStream = System.out;

    @Override
    public void println(Question question) {

        var value = new QuestionFormatter(question).fullQuestion();

        printStream.println(value);
    }

}
