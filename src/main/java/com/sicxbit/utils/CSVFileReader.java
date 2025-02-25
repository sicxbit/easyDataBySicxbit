package com.sicxbit.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


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
           
            String[] headers = reader.readNext();
            if (headers == null) {
                System.out.println("ERROR: The file is empty.");
                return;
            }
            //init mapping
            Map<String, Integer> nullCounts = new HashMap<>();
            for (String header : headers) {
                nullCounts.put(header, 0);
            }

            String[] row;
            while ((row = reader.readNext()) != null) {
                for (int i = 0; i < headers.length; i++) {
                    // Check if the current cell is null or empty
                    if (i >= row.length || row[i] == null || row[i].trim().isEmpty()) {
                        nullCounts.put(headers[i], nullCounts.get(headers[i]) + 1);
                    }
                }
            }

           
            System.out.println("Null Values in Each Column:");
            for (Map.Entry<String, Integer> entry : nullCounts.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " null(s)");
            }
            boolean hasNull = nullCounts.values().stream().anyMatch(count -> count >0);
            if (hasNull) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("\n what would you like to do with the null values");
                System.out.println("1. Drop the rows with null values");
                System.out.println("2. Replace null values with a specific values");
                System.out.println("3. Do nothing (Return to main menu)");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        dropRowsWithNA(filepath);
                        break;
                    case 2:
                        System.out.print("Enter the value to replace the NA");
                        String replacementValue = scanner.nextLine();
                        replaceValues(filepath, replacementValue);
                        break;
                    case 3:
                        System.out.println("Returning to main menu");
                        break;
                }
            }
        } catch (CsvValidationException | IOException e) {
            System.out.println("Error reading the CSV file.");
            e.printStackTrace();
        }
    }
    public void replaceValues(String filepath, String replacementValue) throws IOException, CsvValidationException {
        File inputFile = new File(filepath);
        File tempFile = new File("temp.csv");

        if (tempFile.exists()) {
            if (!tempFile.delete()) {
                System.out.println("Error: Could not delete existing temp file.");
                return;
            }
        }

        try (CSVReader reader = new CSVReader(new FileReader(inputFile));
             CSVWriter writer = new CSVWriter(new FileWriter(tempFile))) {

            String[] headers = reader.readNext();
            if (headers == null) {
                System.out.println("ERROR: The file is empty.");
                return;
            }

            writer.writeNext(headers);

            String[] row;
            int replacedCount = 0;

            while ((row = reader.readNext()) != null) {
                for (int i = 0; i < row.length; i++) {
                    if (row[i] == null || row[i].trim().isEmpty()) {
                        row[i] = replacementValue;
                        replacedCount++;
                    }
                }
                writer.writeNext(row); // Write the updated row to the new file
            }

            System.out.println("Replaced " + replacedCount + " null/empty values with '" + replacementValue + "'.");

            // Flush the writer to ensure all data is written to the file
            writer.flush();

        }

        // Attempt to delete the original file
        if (inputFile.delete()) {
            // Rename the temp file to the original file name
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Error: Could not rename temp file to original file.");
                // Attempt to restore the original file from a backup (if any)
                if (inputFile.createNewFile()) {
                    System.out.println("Created a new empty file as the original file could not be restored.");
                } else {
                    System.out.println("Failed to create a new file. Data may be lost.");
                }
            }
        } else {
            System.out.println("Error: Could not delete the original file.");
            // Provide additional debugging information
            System.out.println("File path: " + inputFile.getAbsolutePath());
            System.out.println("File exists: " + inputFile.exists());
            System.out.println("File writable: " + inputFile.canWrite());
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
    public void dropRowsWithNA(String filepath)throws IOException, CsvValidationException {
        File inputFile = new File(filepath);
        File tempFile = new File ("temp.csv");
        try( CSVReader reader = new CSVReader(new FileReader(inputFile));
             CSVWriter writer = new CSVWriter(new FileWriter(tempFile))) {
            String[] headers = reader.readNext();
            if (headers == null) {
                System.out.println("ERROR: the file is empty.");
                return;
            }
            writer.writeNext(headers);
            String[] row;
            int droppedRows = 0 ;
            while ((row = reader.readNext()) != null) {
                boolean hasNull = false;
                for (String cell : row) {
                    if (cell == null || cell.trim().isEmpty()){
                        hasNull = true;
                        break;
                    }
                }
                if (hasNull){
                    writer.writeNext(row);
                }else{
                    droppedRows++;
                }
                System.out.println("Dropped total of "+droppedRows+" rows");
            }
            if(inputFile.delete()){
                if(!tempFile.renameTo(inputFile)) {
                    System.out.println("Error: Could not rename temp file to original file.");
                }else {
                    System.out.println("Error: Could not delete the original file.");
                }
            }
        }
    }
}
