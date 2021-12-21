package ru.otus.spring.dao;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.formatter.QuestionFormatter;
import ru.otus.spring.service.CsvResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionDaoCsvTest {

    @Mock
    private CsvResourceLoader csvResourceLoader;

    @Mock
    private QuestionFormatter questionFormatter;

    @Test
    public void whenGetCsvDaoFilenameSuccess() throws IOException, CsvException {

        List<String[]> questions = new ArrayList<>();
        questions.add(new String[]{"question1?", "answer1", "*answer2"});
        questions.add(new String[]{"question2?", "answer1", "*answer2"});
        questions.add(new String[]{"question3?", "answer1", "*answer2"});

        given(csvResourceLoader.readData(anyString()))
                .willReturn(questions);

        var config = new AppConfig("test-questions.csv", 40, "ru-RU");
        var dao = new QuestionDaoCsv(config, csvResourceLoader, questionFormatter);

        assertThat(dao.getQuestions())
                .isNotNull()
                .hasSize(3);
    }

    @Test
    public void whenGetEmptyCsvDaoFilenameSuccess() throws IOException, CsvException {

        given(csvResourceLoader.readData(anyString()))
                .willReturn(new ArrayList<>());

        var config = new AppConfig("test-empty-questions.csv", 40, "ru-RU");
        var dao = new QuestionDaoCsv(config, csvResourceLoader, questionFormatter);

        assertThat(dao.getQuestions())
                .isNotNull()
                .hasSize(0);
    }
}