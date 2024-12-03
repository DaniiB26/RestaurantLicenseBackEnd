package com.example.restaurants.controller.requestClasses;

import com.example.restaurants.model.Reservation;
import com.example.restaurants.model.Restaurant;
import com.example.restaurants.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDTO {
    private String id;
    private String userId;
    private String restaurantId;
    private Date dataRezervare;
    private Date creareRezervare;
    private String status;
    private Integer numarPersoane;
    private String specificatii;
    private String userFullName;
    private double userAvgGrade;
    private String restaurantName;
    private boolean isRated;

    public ReservationDTO(Reservation reservation, User user, Restaurant restaurant) {
        this.id = reservation.getId();
        this.userId = reservation.getUserId();
        this.restaurantId = reservation.getRestaurantId();
        this.dataRezervare = reservation.getDataRezervare();
        this.creareRezervare = reservation.getCreareRezervare();
        this.status = reservation.getStatus();
        this.numarPersoane = reservation.getNumarPersoane();
        this.specificatii = reservation.getSpecificatii();
        this.userFullName = user.getFullName();
        this.userAvgGrade = user.getAvgGrade();
        this.restaurantName = restaurant.getNume();
    }
}

