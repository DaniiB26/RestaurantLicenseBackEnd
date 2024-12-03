package com.example.restaurants.service;

import com.example.restaurants.model.Notification;
import com.example.restaurants.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotificationForReservation(String userId, String restaurantId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setRestaurantId(restaurantId);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(new Date());

        notificationRepository.save(notification);
    }

    public Notification createNotificationForStatus(Notification notification) {
        notification.setCreatedAt(new Date());
        notification.setRead(false);
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    public void markNotificationsAsRead(String userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndReadFalse(userId);
        for (Notification notification : notifications) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
}


