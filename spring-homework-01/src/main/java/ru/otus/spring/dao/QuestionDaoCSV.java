package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.spring.domain.Question;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDaoCSV implements QuestionDao {

    List<String[]> csvData;

    public QuestionDaoCSV(String questionsFile) throws IOException, CsvException {
        var file = this.getClass().getClassLoader().getResource(questionsFile);
        CSVReader reader = new CSVReader(new FileReader(file.getFile()));
        csvData = reader.readAll();
    }


    @Override
    public List<Question> findAllQuestions() {
        List<Question> questions = new ArrayList<>();
        for (var quest : csvData) {
            questions.add(new Question(quest[0], List.of(Arrays.copyOfRange(quest, 1, quest.length))));
        }
        return questions;
    }
}
