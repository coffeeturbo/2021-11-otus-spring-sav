package dao;

import com.opencsv.exceptions.CsvException;
import domain.Question;
import exception.QuestionsNotFoundException;
import service.IOService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDaoCSV implements QuestionDao {

    private final String questionsFile;
    private final IOService ioService;

    public QuestionDaoCSV(String questionsFile, IOService ioService) {
        this.questionsFile = questionsFile;
        this.ioService = ioService;
    }

    @Override
    public List<Question> findAllQuestions() throws QuestionsNotFoundException {

        List<String[]> csvData = null;
        try {
            csvData = ioService.readData(questionsFile);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        if (csvData.isEmpty()) {
            throw new QuestionsNotFoundException("В файле не найдено вопросов");
        }

        List<Question> questions = new ArrayList<>(csvData.size());

        for (var quest : csvData) {
            questions.add(new Question(Arrays.asList(quest)));
        }

        return questions;
    }
}
