package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public interface CsvResourceLoader {
    List<String[]> readData(String questionsFile) throws IOException, CsvException;
}
