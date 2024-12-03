package com.example.restaurants.service;

import com.example.restaurants.model.Restaurant;
import com.example.restaurants.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RestaurantRepository restaurantRepository;
    public List<Restaurant> allRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> findRestaurantsByCity(String oras) {
        logger.info("Searching for restaurants in city: {}", oras);
        List<Restaurant> restaurants = restaurantRepository.findByOras(oras);
        logger.info("Number of restaurants found: {}", restaurants.size());
        return restaurants;
    }

    public Restaurant findRestaurantByName(String name) {
        logger.info("Searching for restaurant by name: {}", name);
        Restaurant restaurantName = restaurantRepository.findByNume(name);
        logger.info("Uite ce am gasit: {}", restaurantName.getNume());
        return restaurantName;
    }

    public List<Restaurant> findRestaurantsByFilters(String oras, List<String> tipuri, Double ratingMin, Integer pretMin) {
        Query query = new Query();

        if (oras != null) {
            query.addCriteria(Criteria.where("oras").is(oras));
        }
        if (tipuri != null && !tipuri.isEmpty()) {
            query.addCriteria(Criteria.where("tipRestaurant").in(tipuri));
        }
        if (ratingMin != null) {
            query.addCriteria(Criteria.where("rating_mediu").gte(ratingMin));
        }
        if (pretMin != null) {
            query.addCriteria(Criteria.where("pret").gte(pretMin));
        }

        return mongoTemplate.find(query, Restaurant.class);
    }

    public Restaurant getRestaurantByManagerId(String managerId) {
        logger.info("Fetching restaurant for manager ID: {}", managerId);
        Restaurant restaurant = restaurantRepository.findByManagerId(managerId);
        if (restaurant != null) {
            logger.info("Found restaurant: {}", restaurant);
        } else {
            logger.warn("No restaurant found for manager ID: {}", managerId);
        }
        return restaurant;
    }

    public Restaurant findRestaurantById(String id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public List<Restaurant> findSimilarRestaurants(String restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (!restaurantOptional.isPresent()) {
            return Collections.emptyList();
        }
        Restaurant restaurant = restaurantOptional.get();
        List<Restaurant> similarRestaurants = restaurantRepository.findRestaurantsByTipRestaurantInAndOras(restaurant.getTipRestaurant(), restaurant.getOras());

        return similarRestaurants.stream()
                .filter(r -> !r.getId().equals(restaurantId))
                .collect(Collectors.toList());
    }
}
