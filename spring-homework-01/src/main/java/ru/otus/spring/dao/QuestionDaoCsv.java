package ru.otus.spring.dao;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;


@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionDaoCsv implements QuestionDao {

    String questionsFile;

    public QuestionDaoCsv(@org.springframework.beans.factory.annotation.Value("${app.questions-file}") String questionsFile) {
        this.questionsFile = questionsFile;
    }

    @Override
    public String getCsvDaoFilename() {
        return questionsFile;
    }
}
