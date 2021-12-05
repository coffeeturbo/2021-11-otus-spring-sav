package ru.otus.spring.dao;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionDaoImplTest {

    @Test
    public void whenGetCsvDaoFilenameSuccess() {
        QuestionDao dao = new QuestionDaoImpl("test-questions.csv");

        assertThat(dao.getCsvDaoFilename())
                .isNotNull()
                .contains("test-questions.csv");
    }
}