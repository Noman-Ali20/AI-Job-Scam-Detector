package com.jobscanner.AIproject.ai;



import com.jobscanner.AIproject.entity.ScamCompany;
import com.jobscanner.AIproject.entity.ScamRule;
import com.jobscanner.AIproject.repository.ScamCompanyRepository;
import com.jobscanner.AIproject.repository.ScamRuleRepository;
import com.jobscanner.AIproject.service.CompanyVerificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import java.util.ArrayList;

@Component
public class FraudDetectionEngine {
	
	@Autowired
	private CompanyVerificationService companyVerificationService;

    @Autowired
    private ScamRuleRepository scamRuleRepository;
    
    @Autowired
    private ScamCompanyRepository scamCompanyRepository;

    public Result analyzeText(String text, String email, String companyName) {

        int score = 0;

        if(text == null) text = "";
        text = text.toLowerCase();

        // 1️⃣ Scam keyword rules
        List<ScamRule> rules = scamRuleRepository.findAll();

        for (ScamRule rule : rules) {
            if(text.contains(rule.getKeyword().toLowerCase())) {
                score += rule.getWeight();
            }
        }

        // 2️⃣ Company verification (soft penalty)
        if(companyName != null && !companyVerificationService.verifyCompany(companyName)){
            score += 10;
        }

        // 3️⃣ Known scam companies
        List<ScamCompany> scamCompanies = scamCompanyRepository.findAll();

        for(ScamCompany company : scamCompanies){
            if(text.contains(company.getCompanyName().toLowerCase())){
                score += 40;
            }
        }

        // 4️ Email Domain Risk
        score += EmailDomainChecker.checkDomainRisk(email, companyName);

        // 5️⃣ Final classification
        String result;
        String description;

        if(score >= 70){
            result = "FAKE";
            description = "Message contains strong known scam keywords.";
        }
        else if(score >= 25){
            result = "SUSPICIOUS";
            description = "Email domain does not match the company or appears to be a public email provider or Message contain some scam keywords.";
        }
        else{
            result = "SAFE";
            description = "No major scam indicators detected in the message.";
        }

        return new Result(score, result, description);
    }

    // Optional Overloaded Method (Backward Compatibility)
    public Result analyzeText(String text) {
        return analyzeText(text, null, null);
    }

    // Inner Response Class
    public static class Result {

        public int score;
        public String result;
        public String description;

        public Result(int score, String result, String description) {
            this.score = score;
            this.result = result;
            this.description = description;
        }
    }
    
    
    
}