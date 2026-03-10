package com.jobscanner.AIproject.controller;



import com.jobscanner.AIproject.ai.FraudDetectionEngine;
import com.jobscanner.AIproject.ai.ImageOcrUtil;
import com.jobscanner.AIproject.service.ScanService;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.jobscanner.AIproject.ai.PdfReaderUtil;


@RestController
@RequestMapping("/scan")
@CrossOrigin(origins = "http://localhost:4200")
public class ScanController {

    @Autowired
    private ScanService service;
    
    @Autowired
    private ImageOcrUtil imageOcrUtil;

 // 1️⃣ Text + Email scan
    @PostMapping
    public FraudDetectionEngine.Result scan(
            @RequestParam String text,
            @RequestParam String email,
            @RequestParam String company,
            HttpServletRequest request){

        String token = request.getHeader("Authorization");

        System.out.println("TOKEN RECEIVED: " + token);

        return service.scanText(text, email, company, token);
    }

    // 2️⃣ PDF Upload scan
    @PostMapping("/upload")
    public FraudDetectionEngine.Result uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String company,
            HttpServletRequest request) throws Exception {

        String token = request.getHeader("Authorization");

        String text = PdfReaderUtil.extractText(file.getInputStream());

        return service.scanText(text, email, company, token);
    }
    
   
    
    @PostMapping("/image")
    public FraudDetectionEngine.Result scanImage(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws Exception {

        String token = request.getHeader("Authorization");

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        File tempFile = File.createTempFile("upload", extension);
        file.transferTo(tempFile);

        String extractedText = imageOcrUtil.extractText(tempFile);

        System.out.println("Extracted Text: " + extractedText);

        return service.scanText(extractedText, null, null, token);
    }
}