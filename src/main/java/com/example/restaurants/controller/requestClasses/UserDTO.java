package com.example.restaurants.controller.requestClasses;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private double avgGrade;
    private Integer totalRatings;
    private boolean isManager;

    public UserDTO() {
    }

    public UserDTO(String id, String fullName, String email, String phoneNumber, double avgGrade, Integer totalRatings, boolean isManager) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avgGrade = avgGrade;
        this.totalRatings = totalRatings;
        this.isManager = isManager;
    }
}
