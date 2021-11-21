package ru.otus.spring.dao;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoCSVTest {

    @Test
    public void whenSuccess() throws IOException, CsvException {
        QuestionDao dao = new QuestionDaoCSV("test-questions.csv");
        assertEquals(5, dao.findAllQuestions().size());
    }
}