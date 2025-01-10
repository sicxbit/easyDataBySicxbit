package com.sicxbit.easyData;

import java.io.File;
import java.util.Scanner;

import com.sicxbit.utils.CSVFileReader;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter the path of the CSV file: ");
        String filepath = scanner.nextLine();
        File file = new File(filepath);
        if (!file.exists()){
            System.out.println("Error: File does not exist. Please provide a valid path.");
            scanner.close();
            return;
        }
        if(!filepath.endsWith(".csv")){
            System.out.println("Please provide a csv document");
            scanner.close();
            return;
        }

        CSVFileReader fileReader = new CSVFileReader();

        try {
            fileReader.readCSV(filepath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
