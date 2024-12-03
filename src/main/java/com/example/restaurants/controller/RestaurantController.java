package com.example.restaurants.controller;

import com.example.restaurants.controller.requestClasses.FilterRequest;
import com.example.restaurants.model.Restaurant;
import com.example.restaurants.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Permite accesul doar de la frontend
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return new ResponseEntity<List<Restaurant>>(restaurantService.allRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/search/by-city/{city}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCity(@PathVariable String city) {
        List<Restaurant> restaurants = restaurantService.findRestaurantsByCity(city);
        if (restaurants.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @PostMapping("/filters")
    public List<Restaurant> findRestaurantsByFilters(
            @RequestBody FilterRequest filterRequest) {
        logger.info("Received request {}", filterRequest.getOras());
        return restaurantService.findRestaurantsByFilters(
                filterRequest.getOras(),
                filterRequest.getTipuri(),
                filterRequest.getRatingMin(),
                filterRequest.getPretMin()
        );
    }

    @GetMapping("/by-name/{nume}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String nume) {
        Restaurant restaurant = restaurantService.findRestaurantByName(nume);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PostMapping("/by-manager")
    public ResponseEntity<Restaurant> getRestaurantByManagerId(@RequestBody Map<String, String> request) {
        String managerId = request.get("managerId");
        logger.info("Received request to get restaurant by manager ID: {}", managerId);

        try {
            Restaurant restaurant = restaurantService.getRestaurantByManagerId(managerId);
            if (restaurant != null) {
                logger.info("Found restaurant for manager ID {}: {}", managerId, restaurant);
                return ResponseEntity.ok(restaurant);
            } else {
                logger.warn("No restaurant found for manager ID: {}", managerId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching restaurant for manager ID: {}", managerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String id) {
        logger.error("se face cautarea? {}", id);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/similar/{restaurantId}")
    public ResponseEntity<List<Restaurant>> getSimilarRestaurants(@PathVariable String restaurantId) {
        logger.info("Searching for similar restaurants for ID: {}", restaurantId);
        List<Restaurant> similarRestaurants = restaurantService.findSimilarRestaurants(restaurantId);
        return ResponseEntity.ok(similarRestaurants);
    }
}
