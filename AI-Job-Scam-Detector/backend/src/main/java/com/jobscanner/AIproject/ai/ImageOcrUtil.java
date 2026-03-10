package com.jobscanner.AIproject.ai;



import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ImageOcrUtil {

    @Value("${tesseract.path}")
    private String tesseractPath;

    public String extractText(File file) {

        try {
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath(tesseractPath + "/tessdata");
            return tesseract.doOCR(file);
        }
        catch (Exception e){
            return "";
        }
    }
}
