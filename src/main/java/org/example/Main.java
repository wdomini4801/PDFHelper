package org.example;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                runApp();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Wystąpił nieoczekiwany błąd: " + e.getMessage(),
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static File selectFile(String dialogTitle, boolean isSaveMode) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Pliki PDF", "pdf"));

        int result = isSaveMode ? fileChooser.showSaveDialog(null) : fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (isSaveMode && !selectedFile.getName().toLowerCase().endsWith(".pdf")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".pdf");
            }

            return selectedFile;
        } else {
            return null;
        }
    }

    private static void runApp() {
        File inputFile = selectFile("Wybierz plik PDF do odczytu", false);
        if (inputFile == null) {
            System.err.println("Nie wybrano pliku wejściowego.");
            return;
        }

        File outputFile = selectFile("Wybierz lokalizację i nazwę pliku wyjściowego", true);
        if (outputFile == null) {
            System.err.println("Nie wybrano lokalizacji do zapisu.");
            return;
        }

        String pagesInput = JOptionPane.showInputDialog(null,
                "Podaj numery stron do wyodrębnienia (np. 1,3,5):");
        if (pagesInput == null || pagesInput.isEmpty()) {
            System.err.println("Nie podano numerów stron.");
            return;
        }

        PDFPageExtractor extractor = new PDFPageExtractor();

        try {
            extractor.extractPages(inputFile, outputFile, extractor.parsePageNumbers(pagesInput));
            JOptionPane.showMessageDialog(null,
                    "Plik został zapisany w: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Wystąpił błąd: " + e.getMessage(),
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Nieprawidłowy format numerów stron.",
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
