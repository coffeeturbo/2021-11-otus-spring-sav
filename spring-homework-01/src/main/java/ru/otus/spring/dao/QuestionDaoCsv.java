package ru.otus.spring.dao;

import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.QuestionsFilename;
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

    private final QuestionsFilename questionsFilename;
    private final CsvResourceLoader loader;
    private final QuestionFormatter formatter;

    public QuestionDaoCsv(
            QuestionsFilename questionsFilename,
            CsvResourceLoader loader,
            QuestionFormatter formatter
    ) {
        this.questionsFilename = questionsFilename;
        this.loader = loader;
        this.formatter = formatter;
    }

    @Override
    public List<Question> getQuestions() {

        List<String[]> data;
        try {
            data = loader.readData(questionsFilename.getQuestionsFileName());
        } catch (IOException | CsvException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }

        return data.stream()
                .map(formatter::parseFromStringArray)
                .collect(Collectors.toList());
    }
}
