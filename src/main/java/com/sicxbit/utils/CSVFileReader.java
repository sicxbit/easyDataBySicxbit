package com.sicxbit.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class CSVFileReader {
    public void readCSV(String filepath) throws CsvValidationException, IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                System.out.println(String.join(",", line));
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }
}
