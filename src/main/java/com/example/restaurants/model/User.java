package com.example.restaurants.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private double avgGrade = -1.0;
    private Integer totalRatings = 0;
    private boolean isManager = false;

    public User(String fullName, String email, String phoneNumber, String passwordHash) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.avgGrade = -1.0;
        this.totalRatings = 0;
        this.isManager = false;
    }

}
