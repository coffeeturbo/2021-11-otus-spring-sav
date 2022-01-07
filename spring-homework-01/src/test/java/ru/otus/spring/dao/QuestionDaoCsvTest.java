package ru.otus.spring.dao;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.config.AppConfigHolder;
import ru.otus.spring.domain.AnswerVariant;
import ru.otus.spring.domain.Question;
import ru.otus.spring.formatter.QuestionFormatter;
import ru.otus.spring.service.CsvResourceLoader;
import ru.otus.spring.service.CsvResourceLoaderIml;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuestionDaoCsvTest {

    @Configuration
    static class DaoConfig {
        @Bean
        CsvResourceLoader csvResourceLoader() {
            return new CsvResourceLoaderIml();
        }

        @Bean
        QuestionFormatter questionFormatter() {
            return new QuestionFormatter();
        }

        @Bean
        AppConfigHolder appConfigHolder() {
            return new AppConfigHolder("questions/questions", ".csv", 40, "ru-RU");
        }

    }

    @Autowired
    private CsvResourceLoader csvResourceLoader;

    @Autowired
    private QuestionFormatter questionFormatter;

    @Autowired
    private AppConfigHolder config;


    @Test
    @DisplayName("Находим вопросы на русском")
    public void whenGetRUCsvDaoFilenameSuccess() {

        var question = new Question("Как звали избранного в ХФ. МАТРИЦА?",
                List.of(
                        new AnswerVariant("Нео", true),
                        new AnswerVariant("Картмен", false),
                        new AnswerVariant("Мистер Андерсен", false)
                )
        );

        var dao = new QuestionDaoCsv(config, csvResourceLoader, questionFormatter);
        assertThat(true).isTrue();
        assertThat(dao.getQuestions())
                .isNotNull()
                .hasSize(5)
                .contains(question);
    }

    @Test
    @DisplayName("Находим вопросы на английском")
    public void whenGetENCsvDaoFilenameSuccess() {
        var config = new AppConfigHolder("questions/questions", ".csv", 40, "en-EN");
        var question = new Question("What was the name of chosen one in cinema Matrix?",
                List.of(
                        new AnswerVariant("Neo", true),
                        new AnswerVariant("Cartman", false),
                        new AnswerVariant("Mr. Anderson", false)
                )
        );

        var dao = new QuestionDaoCsv(config, csvResourceLoader, questionFormatter);

        assertThat(true).isTrue();
        assertThat(dao.getQuestions())
                .isNotNull()
                .hasSize(5)
                .contains(question);
    }

    @Test
    @DisplayName("Не находим вопросы на русском - файл не найден")
    public void whenGetEmptyCsvDaoFilenameSuccess() throws IOException, CsvException {

        var config = new AppConfigHolder("test-empty-questions", ".csv", 40, "ru-RU");
        var dao = new QuestionDaoCsv(config, csvResourceLoader, questionFormatter);

        assertThat(dao.getQuestions())
                .isNotNull()
                .isEmpty();
    }
}