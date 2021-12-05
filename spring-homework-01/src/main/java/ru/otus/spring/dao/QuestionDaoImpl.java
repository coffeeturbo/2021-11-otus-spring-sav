package ru.otus.spring.dao;

import lombok.Value;



@Value
public class QuestionDaoImpl implements QuestionDao {

    String questionsFile;

    @Override
    public String getCsvDaoFilename() {
        return questionsFile;
    }
}
