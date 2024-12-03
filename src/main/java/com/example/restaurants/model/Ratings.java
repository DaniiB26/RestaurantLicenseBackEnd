package com.example.restaurants.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {
    private double food;
    private double service;
    private double value;
    private double atmosphere;
}
