package com.jobscanner.AIproject.service;

import com.jobscanner.AIproject.ai.FraudDetectionEngine;
import com.jobscanner.AIproject.entity.Scan;
import com.jobscanner.AIproject.entity.User;
import com.jobscanner.AIproject.repository.ScanRepository;
import com.jobscanner.AIproject.repository.UserRepository;
import com.jobscanner.AIproject.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScanService {

    @Autowired
    private FraudDetectionEngine engine;

    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public FraudDetectionEngine.Result scanText(String text,
                                                String email,
                                                String companyName,
                                                String token) {

        FraudDetectionEngine.Result result =
                engine.analyzeText(text, email, companyName);


        // Extract user from token
        if(token == null || !token.startsWith("Bearer ")){
        	throw new RuntimeException("Invalid or Missing Token");
        	}

        	String jwt = token.substring(7);
        	String userEmail = jwtUtil.extractEmail(jwt);

        User user = userRepository.findByEmailIgnoreCase(userEmail);


        // Save scan
        Scan scan = new Scan();

        scan.setInputText(text);
        scan.setRiskScore(result.score);
        scan.setResult(result.result);
        scan.setScanDate(LocalDateTime.now());

        scan.setUser(user);   // 

        scanRepository.save(scan);

        return result;
    }

}