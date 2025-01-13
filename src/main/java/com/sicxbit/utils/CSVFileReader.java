package com.sicxbit.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class CSVFileReader {
    public void displayHeaders(String filepath) throws CsvValidationException, IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            String[] headers = reader.readNext();
            if (headers != null) {
                System.out.println("Headers: " + String.join(", ", headers));
            } else {
                System.out.println("No headers found in the file.");
            }
        }
    }

    public void countRows(String filepath) throws CsvValidationException, IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            int rowCount = 0;
            while (reader.readNext() != null) {
                rowCount++;
            }
            System.out.println("Total rows: " + rowCount);
        }
    }
}
