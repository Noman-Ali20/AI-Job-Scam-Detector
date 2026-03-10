package com.jobscanner.AIproject.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobscanner.AIproject.entity.Scan;
import com.jobscanner.AIproject.entity.User;

public interface ScanRepository extends JpaRepository<Scan, Integer> {
	long countByResult(String result);

    long count();
    
    List<Scan> findByUser(User user);
    List<Scan> findTop10ByUserOrderByScanDateDesc(User user);
    
    //it is for admin only
    List<Scan> findTop10ByOrderByScanDateDesc();
    
    @Query("""
    		SELECT
    		COUNT(s) as TOTAL_SCANS,
    		SUM(CASE WHEN s.result='FAKE' THEN 1 ELSE 0 END) as FAKE,
    		SUM(CASE WHEN s.result='SAFE' THEN 1 ELSE 0 END) as SAFE,
    		SUM(CASE WHEN s.result='SUSPICIOUS' THEN 1 ELSE 0 END) as SUSPICIOUS
    		FROM Scan s
    		""")
    		Map<String,Object> getGlobalStats();
    

    @Query("""
    		SELECT
    		COUNT(s) as TOTAL_SCANS,
    		SUM(CASE WHEN s.result='FAKE' THEN 1 ELSE 0 END) as FAKE,
    		SUM(CASE WHEN s.result='SAFE' THEN 1 ELSE 0 END) as SAFE,
    		SUM(CASE WHEN s.result='SUSPICIOUS' THEN 1 ELSE 0 END) as SUSPICIOUS
    		FROM Scan s
    		WHERE s.user.id = :userId
    		""")
    		Map<String,Object> getUserStats(Integer userId);
    
}