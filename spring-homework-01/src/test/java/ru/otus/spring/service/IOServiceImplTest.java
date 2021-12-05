package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.AnswerVariant;
import ru.otus.spring.domain.Question;

import java.io.PrintStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IOServiceImplTest {

    private static IOServiceImpl ioService;

    @Mock
    private PrintStream printStream;

    @BeforeAll()
    static void init() {
        ioService = new IOServiceImpl();
    }


    @Test
    void whenPrintln() {
        doNothing().when(printStream).println(any(String.class));

        ioService.setPrintStream(printStream);

        var quest = new Question("1?", List.of(new AnswerVariant("ansswer", true)));
        ioService.println(quest);

        verify(printStream, times(1)).println(any(String.class));
    }
}