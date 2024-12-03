package com.example.restaurants.repository;

import com.example.restaurants.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findReviewsByRestaurantId(String restaurantId);

    List<Review> findReviewsByUserId(String userId);
}
