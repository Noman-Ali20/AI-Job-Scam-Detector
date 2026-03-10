package com.jobscanner.AIproject.controller;



import com.jobscanner.AIproject.config.JwtUtil;
import com.jobscanner.AIproject.dto.LoginRequest;
import com.jobscanner.AIproject.entity.User;
import com.jobscanner.AIproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
    	
    	//System.out.println("Email from request: "+request.getEmail());
    	//System.out.println("Password from request: "+request.getPassword());

    	User user = userRepository.findByEmailIgnoreCase(request.getEmail());

    	//System.out.println("User from DB: "+user);

    //User user = userRepository.findByEmail(request.getEmail());

    if(user == null){
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    .body("Invalid Email");
    }

    if(!user.getPassword().equals(request.getPassword())){
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    .body("Invalid Password");
    }

    String token = JwtUtil.generateToken(user.getEmail());

    return ResponseEntity.ok(token);

    }
}
