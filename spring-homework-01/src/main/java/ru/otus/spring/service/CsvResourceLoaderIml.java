package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
public class CsvResourceLoaderIml implements CsvResourceLoader {

    @Override
    public List<String[]> readData(String questionsFile) throws IOException, CsvException {

        try (var in = getClass().getResourceAsStream("/" + questionsFile)) {
            if (in == null) {
                throw new IOException("File not found");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                CSVReader csvReader = new CSVReader(reader);
                return csvReader.readAll();
            }
        }
    }
}
