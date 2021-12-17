package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;


class CsvResourceLoaderImlTest {

    private static CsvResourceLoader csvResourceLoader;

    @BeforeAll()
    static void init() {
        csvResourceLoader = new CsvResourceLoaderIml();
    }

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
    void whenFileNotFound() throws IOException, CsvException {

        Exception exception = assertThrows(IOException.class, () -> csvResourceLoader.readData("not-exists-test-questions.csv"));

        Assertions.assertThat(exception.getMessage())
                .isEqualTo("File not found");
    }
}