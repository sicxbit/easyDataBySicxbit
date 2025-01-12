package com.sicxbit.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class CSVFileReader {
    public void readCSV(String filepath) throws CsvValidationException, IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            String[] line;
            int rowCount = 0;
            String[] headers = reader.readNext();
            if (headers != null) {
                System.out.println("Columns: "+ String.join(",", headers));
            }
            while ((line = reader.readNext()) != null) {
                rowCount++;
                String.join(",", line);
            }

            System.out.println("Total rows: "+ rowCount+ ", Total columns: "+ headers.length );
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }
}
