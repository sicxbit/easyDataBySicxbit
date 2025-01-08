package com.sicxbit.easyData;

import java.io.FileReader;
import java.util.Scanner;

import com.sicxbit.utils.CSVFileReader;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter the path of the CSV file: ");
        String filepath = scanner.nextLine();


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
