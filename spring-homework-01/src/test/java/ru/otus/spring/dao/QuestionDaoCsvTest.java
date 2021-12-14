package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.formatter.QuestionFormatter;
import ru.otus.spring.service.CsvResourceLoaderIml;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionDaoCsvTest {

    @Test
    public void whenGetCsvDaoFilenameSuccess() {
        QuestionDao dao = new QuestionDaoCsv("test-questions.csv",
                new CsvResourceLoaderIml(),
                new QuestionFormatter()
        );

        assertThat(dao.getQuestions())
                .isNotNull()
                .hasSize(5);
    }

    @Test
    public void whenGetEmptyCsvDaoFilenameSuccess() {
        QuestionDao dao = new QuestionDaoCsv("test-empty-questions.csv",
                new CsvResourceLoaderIml(),
                new QuestionFormatter()
        );

        assertThat(dao.getQuestions())
                .isNotNull()
                .hasSize(0);
    }
}