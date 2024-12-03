package com.example.restaurants.controller;

import com.example.restaurants.controller.requestClasses.ReservationDTO;
import com.example.restaurants.model.Reservation;
import com.example.restaurants.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        reservation.setCreareRezervare(new Date());
        Reservation newReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(newReservation);
    }

    @GetMapping("/user/{userId}")
    public List<ReservationDTO> getReservationsByUserId(@PathVariable String userId) {
        return reservationService.findReservationsByUserId(userId);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<ReservationDTO> getReservationsByRestaurantId(@PathVariable String restaurantId) {
        return reservationService.getReservationsByRestaurantId(restaurantId);
    }

    @PostMapping("/update-status/{id}")
    public ResponseEntity<?> updateReservationStatus(@PathVariable String id, @RequestBody Map<String, String> status) {
        try {
            String newStatus = status.get("status");
            Reservation updatedReservation = reservationService.updateReservationStatus(id, newStatus);
            return ResponseEntity.ok(updatedReservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating reservation status");
        }
    }

    @PostMapping("/{reservationId}/rate-user")
    public ResponseEntity<String> rateUser(
            @PathVariable String reservationId,
            @RequestParam double rating) {
        try {
            reservationService.rateUserForReservation(reservationId, rating);
            return ResponseEntity.ok("User rated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error rating user: " + e.getMessage());
        }
    }
}
