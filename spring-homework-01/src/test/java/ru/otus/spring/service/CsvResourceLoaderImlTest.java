package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = CsvResourceLoaderIml.class)
class CsvResourceLoaderImlTest {

    @Autowired
    private CsvResourceLoader csvResourceLoader;

    @Test
    void whenReadDataSuccess() throws IOException, CsvException {
        var data = csvResourceLoader.readData("test-questions.csv");
        Assertions.assertThat(data)
                .hasSize(5);
    }

    @Test
    void whenFileEmpty() throws IOException, CsvException {
        var data = csvResourceLoader.readData("test-empty-questions.csv");
        Assertions.assertThat(data)
                .hasSize(0);
    }

    @Test
    void whenFileNotFound() throws CsvException {

        Exception exception = assertThrows(IOException.class, () -> csvResourceLoader.readData("not-exists-test-questions.csv"));

        Assertions.assertThat(exception.getMessage())
                .isEqualTo("File not found");
    }
}