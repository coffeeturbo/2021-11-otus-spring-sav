package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.AnswerVariant;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.QuestionsBadFormatException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;
    private final CsvResourceLoader loader;

    @Override
    public List<Question> getQuestions() throws QuestionsBadFormatException {
        List<String[]> data = loader.readData(dao.getCsvDaoFilename());

        if (data == null || data.isEmpty()) {
            throw new QuestionsBadFormatException("Неправильынй формат фала");
        }

        return data.stream()
                .map(QuestionServiceImpl::parseFromStringArray)
                .collect(Collectors.toList());
    }

    private static Question parseFromStringArray(String[] quest) {
        var variants = Arrays.stream(quest)
                .filter(s -> !s.contains("?"))
                .map(s -> new AnswerVariant(s.replace("*", ""), s.contains("*")))
                .collect(Collectors.toList());

        var question = Arrays.stream(quest)
                .filter(s -> s.contains("?"))
                .findFirst()
                .orElse(null);

        return new Question(question, variants);
    }
}
