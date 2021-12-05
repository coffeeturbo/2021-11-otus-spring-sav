package ru.otus.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class CsvResourceLoaderImlTest {

    private static CsvResourceLoader csvResourceLoader;

    @BeforeAll()
    static void init() {
        csvResourceLoader = new CsvResourceLoaderIml();
    }

    @Test
    void readData() {
        var data = csvResourceLoader.readData("test-questions.csv");
        Assertions.assertThat(data)
                .hasSize(5);
    }
}