package ru.otus.spring.dao;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.formatter.QuestionFormatter;
import ru.otus.spring.service.CsvResourceLoader;

import java.util.List;
import java.util.stream.Collectors;


@Component
@Getter
public class QuestionDaoCsv implements QuestionDao {

    private final String questionsFile;
    private final CsvResourceLoader loader;
    private final QuestionFormatter formatter;

    public QuestionDaoCsv(
            @Value("${app.questions-file}") String questionsFile,
            CsvResourceLoader loader,
            QuestionFormatter formatter
    ) {
        this.questionsFile = questionsFile;
        this.loader = loader;
        this.formatter = formatter;
    }

    @Override
    public List<Question> getQuestions() {

        List<String[]> data = loader.readData(questionsFile);

        return data.stream()
                .map(formatter::parseFromStringArray)
                .collect(Collectors.toList());
    }
}
