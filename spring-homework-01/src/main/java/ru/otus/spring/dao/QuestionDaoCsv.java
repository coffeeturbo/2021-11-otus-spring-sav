package ru.otus.spring.dao;

import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.QuestionsFilenameHolder;
import ru.otus.spring.domain.Question;
import ru.otus.spring.formatter.QuestionFormatter;
import ru.otus.spring.service.CsvResourceLoader;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class QuestionDaoCsv implements QuestionDao {

    private final QuestionsFilenameHolder questionsFilenameHolder;
    private final CsvResourceLoader loader;
    private final QuestionFormatter formatter;

    public QuestionDaoCsv(
            QuestionsFilenameHolder questionsFilenameHolder,
            CsvResourceLoader csvResourceLoader,
            QuestionFormatter formatter
    ) {
        this.questionsFilenameHolder = questionsFilenameHolder;
        this.loader = csvResourceLoader;
        this.formatter = formatter;
    }

    @Override
    public List<Question> getQuestions() {

        List<String[]> data;
        try {
            data = loader.readData(questionsFilenameHolder.getQuestionsFileName());
        } catch (IOException | CsvException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }

        return data.stream()
                .map(formatter::parseFromStringArray)
                .collect(Collectors.toList());
    }
}
