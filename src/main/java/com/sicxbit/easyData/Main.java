package com.sicxbit.easyData;

import java.io.File;
import java.util.Scanner;
import com.sicxbit.utils.CSVFileReader;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filepath = null;

        while (true) {

            System.out.println("\n=== CSV Data Analysis App ===");
            System.out.println("1. Set CSV File Path");
            System.out.println("2. Display Headers");
            System.out.println("3. Count Rows");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter the path of the CSV file: ");
                    filepath = scanner.nextLine();
                    File file = new File(filepath);
                    if (!file.exists()) {
                        System.out.println("Error: File does not exist. Please provide a valid path.");
                        filepath = null; // Reset filepath
                    } else if (!filepath.endsWith(".csv")) {
                        System.out.println("Error: Please provide a valid CSV file.");
                        filepath = null;
                    } else {
                        System.out.println("File path set successfully.");
                    }
                    break;

                case 2:
                    if (filepath == null) {
                        System.out.println("Error: No file path set. Please choose option 1 first.");
                    } else {
                        try {
                            CSVFileReader fileReader = new CSVFileReader();
                            fileReader.displayHeaders(filepath);
                        } catch (Exception e) {
                            System.out.println("Error: Unable to read headers.");
                            e.printStackTrace();
                        }
                    }
                    break;

                case 3:
                    if (filepath == null) {
                        System.out.println("Error: No file path set. Please choose option 1 first.");
                    } else {
                        try {
                            CSVFileReader fileReader = new CSVFileReader();
                            fileReader.countRows(filepath);
                        } catch (Exception e) {
                            System.out.println("Error: Unable to count rows.");
                            e.printStackTrace();
                        }
                    }
                    break;

                case 4:
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
