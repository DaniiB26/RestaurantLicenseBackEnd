package com.example.restaurants.service;

import com.example.restaurants.controller.requestClasses.ReservationDTO;
import com.example.restaurants.model.Reservation;
import com.example.restaurants.model.Restaurant;
import com.example.restaurants.model.User;
import com.example.restaurants.repository.ReservationRepository;
import com.example.restaurants.repository.RestaurantRepository;
import com.example.restaurants.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private NotificationService notificationService;

    public Reservation saveReservation(Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);
        Restaurant restaurant = restaurantRepository.findById(reservation.getRestaurantId()).orElse(null);
        if (restaurant != null) {
            String message = "Your reservation request to " + restaurant.getNume() + " has been submitted. Please wait for confirmation.";
            notificationService.createNotificationForReservation(reservation.getUserId(), reservation.getRestaurantId(), message);
        }
        return savedReservation;
    }

    public List<ReservationDTO> findReservationsByUserId(String userId) {
        List<Reservation> reservations = reservationRepository.findReservationsByUserId(userId);
        return reservations.stream().map(this::toReservationDTO).collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsByRestaurantId(String restaurantId) {
        List<Reservation> reservations = reservationRepository.findReservationsByRestaurantId(restaurantId);
        return reservations.stream().map(this::toReservationDTO).collect(Collectors.toList());
    }

    public Reservation updateReservationStatus(String reservationId, String status) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (!reservationOpt.isPresent()) {
            return null;
        }
        Reservation reservation = reservationOpt.get();
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }

    private ReservationDTO toReservationDTO(Reservation reservation) {
        User user = userRepository.findById(reservation.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Restaurant restaurant = restaurantRepository.findById(reservation.getRestaurantId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        return new ReservationDTO(reservation, user, restaurant);
    }

    public void rateUserForReservation(String reservationId, double rating) throws Exception {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new Exception("Reservation not found"));

        User user = userRepository.findById(reservation.getUserId())
                .orElseThrow(() -> new Exception("User not found"));

        if (user.getAvgGrade() == -1) {
            user.setAvgGrade(rating);
            user.setTotalRatings(1);
        } else {
            double newAvgGrade = (user.getAvgGrade() * user.getTotalRatings() + rating) / (user.getTotalRatings() + 1);
            user.setAvgGrade(newAvgGrade);
            user.setTotalRatings(user.getTotalRatings() + 1);
        }

        userRepository.save(user);

        reservation.setRated(true);
        reservationRepository.save(reservation);
    }


}
