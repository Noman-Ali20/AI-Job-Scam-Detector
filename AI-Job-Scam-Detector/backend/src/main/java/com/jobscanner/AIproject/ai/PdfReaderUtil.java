package com.jobscanner.AIproject.ai;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.InputStream;

public class PdfReaderUtil {

    public static String extractText(InputStream inputStream) {

        try {
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();
            return text;
        }
        catch(Exception e){
            return "";
        }
    }
}
