package org.example;

import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj ścieżkę do pliku PDF: ");
        String inputFilePath = scanner.nextLine();

        System.out.print("Podaj ścieżkę do nowego pliku PDF: ");
        String outputFilePath = scanner.nextLine();

        System.out.print("Podaj numery stron do wyodrębnienia (np. 1,3,5): ");
        String pagesInput = scanner.nextLine();

        PDFPageExtractor extractor = new PDFPageExtractor();

        try {
            extractor.extractPages(inputFilePath, outputFilePath, extractor.parsePageNumbers(pagesInput));
            System.out.println("Wybrane strony zostały zapisane do: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Wystąpił błąd: " + e.getMessage());
        }
    }
}
