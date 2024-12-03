package com.example.restaurants.controller;

import com.example.restaurants.model.Review;
import com.example.restaurants.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @GetMapping("/restaurant/{restaurantId}")
    public List<Review> getReviewsByRestaurantId(@PathVariable String restaurantId) {
        logger.info("Received request to get reviews for restaurant ID: {}", restaurantId);
        List<Review> reviews = reviewService.findReviewsByRestaurantId(restaurantId);
        if (reviews != null && !reviews.isEmpty()) {
            logger.info("Found {} reviews for restaurant ID: {}", reviews.size(), restaurantId);
        } else {
            logger.warn("No reviews found for restaurant ID: {}", restaurantId);
        }
        return reviews;
    }

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        review.setData(new Date());
        review.setLikes(0);
        review.setDislikes(0);
        return reviewService.saveReview(review);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUserId(@PathVariable String userId) {
        return reviewService.findReviewsByUserId(userId);
    }
}
