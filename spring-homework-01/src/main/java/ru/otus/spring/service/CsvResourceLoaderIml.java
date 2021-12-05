package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CsvResourceLoaderIml implements CsvResourceLoader {

    @Override
    public List<String[]> readData(String questionsFile) {

        try (var in = getClass().getResourceAsStream("/" + questionsFile)) {
            if (in == null) {
                throw new IOException("File not found");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                CSVReader csvReader = new CSVReader(reader);
                return csvReader.readAll();
            } catch (IOException | CsvException e) {
                System.out.println("Error of reading file");
            }

        } catch (Exception e) {
            System.out.println("File: " + questionsFile + " is not found.");
        }
        return null;
    }
}
