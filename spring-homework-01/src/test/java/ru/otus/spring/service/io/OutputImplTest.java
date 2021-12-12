package ru.otus.spring.service.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class OutputImplTest {

    private Output output;
    private PrintStream backup;
    private ByteArrayOutputStream bos;

    @BeforeEach
    void setUp() {

        backup = System.out;
        bos = new ByteArrayOutputStream();
        output = new OutputImpl(new PrintStream(bos));
        System.setOut(new PrintStream(bos));
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