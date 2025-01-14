package com.sicxbit.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CSVFileReader {
    public void debug(String filepath) throws CsvValidationException, IOException{
        try(CSVReader reader = new CSVReader(new FileReader(filepath))){
            String[] firstRow = reader.readNext();
            if (firstRow != null) {
                System.out.println("Headers: " + String.join(", ", firstRow));
            } else {
                System.out.println("No headers found in the file.");
            }

        }
    }
    public void checkNullValues(String filepath) throws CsvValidationException, IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            // Read headers
            String[] headers = reader.readNext();
            if (headers == null) {
                System.out.println("ERROR: The file is empty.");
                return;
            }

            // Initialize a map to track null counts for each column
            Map<String, Integer> nullCounts = new HashMap<>();
            for (String header : headers) {
                nullCounts.put(header, 0); // Initialize counts to zero
            }

            // Process rows and count null/empty values
            String[] row;
            while ((row = reader.readNext()) != null) {
                for (int i = 0; i < headers.length; i++) {
                    // Check if the current cell is null or empty
                    if (i >= row.length || row[i] == null || row[i].trim().isEmpty()) {
                        nullCounts.put(headers[i], nullCounts.get(headers[i]) + 1);
                    }
                }
            }

            // Print results
            System.out.println("Null Values in Each Column:");
            for (Map.Entry<String, Integer> entry : nullCounts.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " null(s)");
            }
        } catch (CsvValidationException | IOException e) {
            System.out.println("Error reading the CSV file.");
            e.printStackTrace();
        }
    }
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
