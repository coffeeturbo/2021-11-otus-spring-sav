package dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import domain.Question;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDaoCSV implements QuestionDao {

    List<String[]> csvData;

    public QuestionDaoCSV(String questionsFile) throws IOException, CsvException {
        var in = getClass().getResourceAsStream("/" + questionsFile);
        if (in == null) {
            throw new FileNotFoundException("file wasn't founded");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        CSVReader csvReader = new CSVReader(reader);
        csvData = csvReader.readAll();
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
