package com.example.restaurants.service;

import com.example.restaurants.model.Ratings;
import com.example.restaurants.model.Restaurant;
import com.example.restaurants.model.Review;
import com.example.restaurants.repository.RestaurantRepository;
import com.example.restaurants.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Review> findReviewsByRestaurantId(String restaurantId) {
        return reviewRepository.findReviewsByRestaurantId(restaurantId);
    }

    public Review saveReview(Review review) {
        reviewRepository.save(review);
        updateRestaurantRatings(review.getRestaurantId());
        return review;
    }

    private void updateRestaurantRatings(String restaurantId) {
        List<Review> reviews = reviewRepository.findReviewsByRestaurantId(restaurantId);
        double totalRating = 0;
        double totalFood = 0;
        double totalService = 0;
        double totalValue = 0;
        double totalAtmosphere = 0;
        int count = reviews.size();
        int splitRatingCount = 0;

        for (Review review : reviews) {
            totalRating += review.getNota();
            if (review.getSplitRating().getFood() != 0) {
                totalFood += review.getSplitRating().getFood();
                totalService += review.getSplitRating().getService();
                totalValue += review.getSplitRating().getValue();
                totalAtmosphere += review.getSplitRating().getAtmosphere();
                splitRatingCount++;
            }
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        restaurant.setRating_mediu(totalRating / count);

        if (splitRatingCount > 0) {
            restaurant.setRatings(new Ratings(
                    totalFood / splitRatingCount,
                    totalService / splitRatingCount,
                    totalValue / splitRatingCount,
                    totalAtmosphere / splitRatingCount
            ));
        }

        restaurantRepository.save(restaurant);
    }

    public List<Review> findReviewsByUserId(String userId) {
        return reviewRepository.findReviewsByUserId(userId);
    }
}
