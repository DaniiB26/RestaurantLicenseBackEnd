package com.example.restaurants.controller;

import com.example.restaurants.controller.requestClasses.LoginRequest;

import com.example.restaurants.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final UserService service;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Login request received: {}", loginRequest);
            service.login(loginRequest.getEmail(), loginRequest.getPassword());
            String token = generateToken(loginRequest.getEmail());
            String userId = service.getUserDTOByEmail(loginRequest.getEmail()).getId();

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", userId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Login failed", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    private String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256,  Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }
}
