package service;

import com.opencsv.exceptions.CsvException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

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
    void readDataFromStream() throws IOException, CsvException {
        var data = ioService.readData("test-questions.csv");
        ioService.setPrintStream(System.out);
        Assertions.assertThat(data)
                .hasSize(5);
    }

    @Test
    void whenPrintln() {

        doNothing().when(printStream).println(isA(String.class));

        ioService.setPrintStream(printStream);
        ioService.println("test");

        verify(printStream).println("test");
    }
}