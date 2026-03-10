package com.jobscanner.AIproject.service;



import com.jobscanner.AIproject.entity.Scan;
import com.jobscanner.AIproject.repository.ScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ScanRepository scanRepository;
    
    public List<Scan> getRecentScans(){

        return scanRepository.findTop10ByOrderByScanDateDesc();

    }

    public Map<String, Long> getStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("TOTAL_SCANS", scanRepository.count());
        stats.put("FAKE", scanRepository.countByResult("FAKE"));
        stats.put("SUSPICIOUS", scanRepository.countByResult("SUSPICIOUS"));
        stats.put("SAFE", scanRepository.countByResult("SAFE"));

        return stats;
    }
}