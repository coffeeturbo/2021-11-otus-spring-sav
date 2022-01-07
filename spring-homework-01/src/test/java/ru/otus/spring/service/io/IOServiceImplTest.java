package ru.otus.spring.service.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.otus.spring.exception.InputVariantMismatchException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.when;

@SpringBootTest
class IOServiceImplTest {

    @Configuration
    static class IOserviceConfig {
        @Bean
        @Scope("prototype")
        ByteArrayOutputStream byteArrayOutputStream() {
            return new ByteArrayOutputStream();
        }
    }

    @Autowired
    private ByteArrayOutputStream byteArrayOutputStream;
    @MockBean
    Input inputStream;
    @MockBean
    InputProvider inputProvider;


    @Test
    void whenPrintln() {
        var ioService = new IOServiceImpl(inputStream, new PrintStream(byteArrayOutputStream));
        ioService.println("test message");

        Assertions.assertThat(byteArrayOutputStream.toString())
                .isEqualTo("test message" + System.lineSeparator());
    }

    @Test
    void whenPrintlnSecondMessage() {
        var ioService = new IOServiceImpl(inputStream, new PrintStream(byteArrayOutputStream));
        ioService.println("second message");

        Assertions.assertThat(byteArrayOutputStream.toString())
                .isEqualTo("second message" + System.lineSeparator());
    }


    @Test
    void whenAskStringSuccess() {
        when(inputStream.nextLine())
                .thenReturn("10");

        var ioService = new IOServiceImpl(inputStream, new PrintStream(byteArrayOutputStream));

        var actual = ioService.askStr("Some question?");
        Assertions.assertThat(actual)
                .isEqualTo("10");
    }

    @Test
    void whenAskStringInputProviderSuccess() {
        when(inputProvider.nextLine())
                .thenReturn("10");

        var ioService = new IOServiceImpl(inputProvider, new PrintStream(byteArrayOutputStream));

        var actual = ioService.askStr("Some question?");
        Assertions.assertThat(actual)
                .isEqualTo("10");
    }

    @Test
    void whenAskIntSuccess() {
        when(inputStream.nextLine())
                .thenReturn("99");

        var ioService = new IOServiceImpl(inputStream, new PrintStream(byteArrayOutputStream));

        var actual = ioService.askInt("Some question?");
        Assertions.assertThat(actual)
                .isEqualTo(99);
    }

    @Test
    void whenAskIntWithLimitSuccess() {
        when(inputStream.nextLine())
                .thenReturn("9");

        var ioService = new IOServiceImpl(inputStream, new PrintStream(byteArrayOutputStream));

        var actual = ioService.askInt("Some question?", 10);
        Assertions.assertThat(actual)
                .isEqualTo(9);
    }

    @Test
    void whenAskIntWithLimitOverflowFailed() {
        when(inputStream.nextLine())
                .thenReturn("9");

        var ioService = new IOServiceImpl(inputStream, new PrintStream(byteArrayOutputStream));

        var exception = org.junit.jupiter.api.Assertions.assertThrows(
                InputVariantMismatchException.class,
                () -> ioService.askInt("Some question?", 7)
        );

        Assertions.assertThat(exception.getMessage())
                .isEqualTo("The inputted answer variant: 9 doesn't exist");
    }

    @Test
    void whenAskIntWithLimitNotNumberFailed() {
        when(inputStream.nextLine())
                .thenReturn("string");

        var ioService = new IOServiceImpl(inputStream, new PrintStream(byteArrayOutputStream));

        var exception = org.junit.jupiter.api.Assertions.assertThrows(
                InputVariantMismatchException.class,
                () -> ioService.askInt("Some question?", 7)
        );

        Assertions.assertThat(exception.getMessage())
                .isEqualTo("The inputted variant is not numeric");
    }


}