package com.example.restaurants.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "restaurants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    private String id;
    private String nume;
    private String oras;
    private String adresa;
    private Coordonate coordonate;
    private String telefon;
    private String email;
    private String site_web;
    private String program;
    private List<String> tipRestaurant;
    private List<MenuItem> meniu;
    private List<String> special_diets;
    private List<String> meals;
    private List<String> features;
    private int capacitate;
    private double rating_mediu;
    private String logo;
    private String thumbnail;
    private List<String> imagini;
    private Ratings ratings;
    private Integer pret;
    private String managerId;
}
