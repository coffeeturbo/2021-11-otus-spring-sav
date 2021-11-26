package service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

@Setter
public class IOServiceImpl implements IOService {

    private PrintStream printStream = System.out;


    @Override
    public void println(String value) {
        printStream.println(value);
    }

    public List<String[]> readData(String filename) throws IOException, CsvException {

        try (var in = getClass().getResourceAsStream("/" + filename)) {
            List<String[]> csvData;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                CSVReader csvReader = new CSVReader(reader);
                csvData = csvReader.readAll();
            }
            return csvData;
        }
    }
}
