package com.example.restaurants.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    public String id;
    public String userId;
    public String userName;
    public String restaurantId;
    public String recenzie;
    public List<MenuItem> recomandari;
    public double nota;
    public Ratings splitRating;
    public Date data;
    public Integer likes;
    public Integer dislikes;
}
