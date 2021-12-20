package ru.otus.spring.service.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
class IOServiceImplTest {

    @Test
    void whenPrintln() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOService output = new IOServiceImpl(System.in, new PrintStream(bos));
        output.println("test message");

        Assertions.assertThat(bos.toString())
                .isEqualTo("test message" + System.lineSeparator());
    }
}