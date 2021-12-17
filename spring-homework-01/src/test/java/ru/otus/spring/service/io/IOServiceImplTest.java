package ru.otus.spring.service.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
class IOServiceImplTest {
    private IOService output;
    private PrintStream backup;
    private ByteArrayOutputStream bos;


    @BeforeEach
    void setUp() {
        backup = System.out;
        bos = new ByteArrayOutputStream();
        output = new IOServiceImpl(System.in, new PrintStream(bos));
    }

    @AfterEach
    void tearDown() {
        System.setOut(backup);
    }

    @Test
    void whenPrintln() {
        output.println("test message");

        Assertions.assertThat(bos.toString())
                .isEqualTo("test message" + System.lineSeparator());
    }
}