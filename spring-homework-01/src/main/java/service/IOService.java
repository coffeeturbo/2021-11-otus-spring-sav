package service;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.List;

public interface IOService {
    void println(String value);

    List<String[]> readData(String filename) throws IOException, CsvException;
}
