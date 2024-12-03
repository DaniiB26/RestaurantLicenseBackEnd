package com.example.restaurants.repository;

import com.example.restaurants.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User getUserByEmail(String email);
    User getUserById(String userId);
    User getUserByPhoneNumber(String phoneNumber);
}
