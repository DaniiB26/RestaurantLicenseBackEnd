package com.example.restaurants.controller;

import com.example.restaurants.model.Notification;
import com.example.restaurants.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification newNotification = notificationService.createNotificationForStatus(notification);
        return ResponseEntity.ok(newNotification);
    }

    @PostMapping("/createReservationNotification")
    public ResponseEntity<Void> createReservationNotification(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String restaurantId = request.get("restaurantId");
        String message = request.get("message");
        notificationService.createNotificationForReservation(userId, restaurantId, message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/mark-read")
    public ResponseEntity<Void> markNotificationsAsRead(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        notificationService.markNotificationsAsRead(userId);
        return ResponseEntity.ok().build();
    }
}

