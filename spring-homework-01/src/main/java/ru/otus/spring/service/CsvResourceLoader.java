package ru.otus.spring.service;

import java.util.List;

public interface CsvResourceLoader {
    List<String[]> readData(String questionsFile);
}
