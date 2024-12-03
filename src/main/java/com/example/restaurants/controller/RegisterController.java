package com.example.restaurants.controller;

import com.example.restaurants.controller.requestClasses.RegisterRequest;
import com.example.restaurants.model.User;
import com.example.restaurants.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/api/register")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {

    private final UserService service;
    private final BCryptPasswordEncoder cryptPasswordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        try {
            logger.info("Register request received: email={}", registerRequest.getEmail());
            if (!registerRequest.getPasswordHash().equals(registerRequest.getConfirmPassword())) {
                throw new ValidationException("Passwords do not match!");
            }
            User user = new User(
                    registerRequest.getFullName(),
                    registerRequest.getEmail(),
                    registerRequest.getPhoneNumber(),
                    registerRequest.getPasswordHash()
            );
            service.registerUser(user);
            return ResponseEntity.ok("Registration successful!");
        } catch (ValidationException e) {
            logger.error("Validation failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Registration failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration. Please try again later.");
        }
    }
}



