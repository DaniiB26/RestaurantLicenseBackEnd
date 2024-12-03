package com.example.restaurants.repository;

import com.example.restaurants.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    public List<Restaurant> findByOras(String oras);
    public Restaurant findByNume(String nume);
    public Restaurant findByManagerId(String managerId);
    List<Restaurant> findRestaurantsByTipRestaurantInAndOras(List<String> tipRestaurant, String oras);

}
