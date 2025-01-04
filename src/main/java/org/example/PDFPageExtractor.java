package org.example;

import org.apache.pdfbox.pdfwriter.compress.CompressParameters;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.Loader;
import java.io.File;
import java.io.IOException;

public class PDFPageExtractor {

    public void extractPages(String inputFilePath, String outputFilePath, int[] pages) throws IOException {
        try (PDDocument inputDocument = Loader.loadPDF(new File(inputFilePath));
             PDDocument outputDocument = new PDDocument()) {

            for (int pageNumber : pages) {
                if (pageNumber > 0 && pageNumber <= inputDocument.getNumberOfPages()) {
                    outputDocument.addPage(inputDocument.getPage(pageNumber - 1));
                } else {
                    System.err.println("Strona " + pageNumber + " nie istnieje w pliku PDF.");
                }
            }

            outputDocument.save(outputFilePath, CompressParameters.NO_COMPRESSION);
        }
    }

    public int[] parsePageNumbers(String pagesInput) {
        String[] pagesArray = pagesInput.split(",");
        int[] pages = new int[pagesArray.length];

        for (int i = 0; i < pagesArray.length; i++) {
            pages[i] = Integer.parseInt(pagesArray[i].trim());
        }

        return pages;
    }
}
