package com.jobscanner.AIproject.ai;



import java.util.Arrays;
import java.util.List;

public class EmailDomainChecker {

    private static final List<String> publicDomains =
            Arrays.asList("gmail.com","yahoo.com","outlook.com","hotmail.com");

    public static int checkDomainRisk(String email, String companyName) {

        if(email == null || !email.contains("@")) return 0;

        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();

        int score = 0;

        // Public email
        if(publicDomains.contains(domain)) {
            score += 30;
        }

        // Suspicious words
        if(domain.contains("job") || domain.contains("career")
                || domain.contains("hr") || domain.contains("online")) {
            score += 20;
        }

        // Company mismatch check
//        if(companyName != null && !domain.contains(companyName.toLowerCase())) {
//            score += 40;
//        }
        if(companyName != null) {
            String shortName = companyName.toLowerCase().split(" ")[0];
            if(!domain.contains(shortName)) {
                score += 20;
            }
        }

        return score;
    }
}