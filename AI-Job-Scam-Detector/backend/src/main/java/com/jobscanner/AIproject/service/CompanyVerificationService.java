package com.jobscanner.AIproject.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyVerificationService {

    @Autowired
    private RestTemplate restTemplate;

    public boolean verifyCompany(String companyName) {

        if(companyName == null) return false;

        companyName = companyName.toLowerCase();

        // Known legitimate companies
        if(companyName.contains("tata")
        		|| companyName.contains("tcs")
            || companyName.contains("infosys")
            || companyName.contains("wipro")
            || companyName.contains("hcl")
            || companyName.contains("reliance")
            || companyName.contains("accenture")
            || companyName.contains("ibm")
            || companyName.contains("google")
            || companyName.contains("microsoft")) {

            return true;
        }

        return false;
    }
}
