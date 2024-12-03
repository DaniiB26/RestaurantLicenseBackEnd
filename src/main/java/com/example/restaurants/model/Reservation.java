package com.example.restaurants.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    public String id;
    public String userId;
    public String restaurantId;
    public Date dataRezervare;
    public Date creareRezervare;
    public String status;
    public Integer numarPersoane;
    public String specificatii;
    public boolean isRated = false;
}
