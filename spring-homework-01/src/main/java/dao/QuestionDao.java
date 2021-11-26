package dao;

import domain.Question;
import exception.QuestionsNotFoundException;

import java.util.List;

public interface QuestionDao {
    List<Question> findAllQuestions() throws QuestionsNotFoundException;
}
