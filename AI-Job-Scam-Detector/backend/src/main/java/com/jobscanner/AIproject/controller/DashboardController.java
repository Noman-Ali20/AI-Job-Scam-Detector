package com.jobscanner.AIproject.controller;



import com.jobscanner.AIproject.config.JwtUtil;
import com.jobscanner.AIproject.entity.Scan;
import com.jobscanner.AIproject.entity.User;
import com.jobscanner.AIproject.repository.ScanRepository;
import com.jobscanner.AIproject.repository.UserRepository;
import com.jobscanner.AIproject.service.DashboardService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ScanRepository scanRepository;

   
    
    @GetMapping("/stats")
    public ResponseEntity<?> getStatistics(HttpServletRequest request){

    String authHeader = request.getHeader("Authorization");

    if(authHeader == null || !authHeader.startsWith("Bearer ")){
    return ResponseEntity
    .status(HttpStatus.UNAUTHORIZED)
    .body("Invalid Token");
    }

    String token = authHeader.substring(7);

    String email = jwtUtil.extractEmail(token);

    User user = userRepository.findByEmailIgnoreCase(email);

    if(user == null){
    return ResponseEntity
    .status(HttpStatus.UNAUTHORIZED)
    .body("User not found");
    }

    if("ADMIN".equals(user.getRole())){
    return ResponseEntity.ok(scanRepository.getGlobalStats());
    }

    return ResponseEntity.ok(scanRepository.getUserStats(user.getId()));

    }
    
    @GetMapping("/recent-scans")
    public List<Scan> getRecentScans(HttpServletRequest request){

    String authHeader = request.getHeader("Authorization");

    if(authHeader == null || !authHeader.startsWith("Bearer ")){
    throw new RuntimeException("Invalid Token");
    }

    String token = authHeader.substring(7);

    String email = jwtUtil.extractEmail(token);

    User user = userRepository.findByEmailIgnoreCase(email);

    if("ADMIN".equals(user.getRole())){
    return scanRepository.findTop10ByOrderByScanDateDesc();
    }

    return scanRepository.findTop10ByUserOrderByScanDateDesc(user);

    }
}