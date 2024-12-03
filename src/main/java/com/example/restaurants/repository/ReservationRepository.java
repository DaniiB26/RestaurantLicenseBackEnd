package com.example.restaurants.repository;

import com.example.restaurants.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findReservationsByUserId(String userId);
    List<Reservation> findReservationsByRestaurantId(String restaurantId);
}
