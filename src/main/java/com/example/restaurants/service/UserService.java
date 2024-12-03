package com.example.restaurants.service;

import com.example.restaurants.controller.requestClasses.UserDTO;
import com.example.restaurants.exceptions.DataChangeException;
import com.example.restaurants.model.User;
import com.example.restaurants.repository.RestaurantRepository;
import com.example.restaurants.repository.UserRepository;
import com.example.restaurants.validation.ValidateUser;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final ValidateUser validateUser;
    @Autowired
    private final BCryptPasswordEncoder cryptPasswordEncoder;

    public UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAvgGrade(user.getAvgGrade());
        dto.setManager(user.isManager());
        return dto;
    }

    public void login(String email, String password) throws IllegalAccessException {
        logger.info("Attempting to login with email: {}", email);
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            logger.error("User not found for email: {}", email);
            throw new IllegalAccessException("Email or password are invalid!");
        }
        if (!cryptPasswordEncoder.matches(password, user.getPasswordHash())) {
            logger.error("Password does not match for email: {}", email);
            throw new IllegalAccessException("Email or password are invalid!");
        }
        logger.info("Login successful for email: {}", email);
    }

    public void registerUser(User user) throws ValidationException {
        try {
            validateUser.validateUser(user);
            if (userRepository.getUserByEmail(user.getEmail()) != null) {
                throw new ValidationException("Email is already taken!");
            }
            if (userRepository.getUserByPhoneNumber(user.getPhoneNumber()) != null) {
                throw new ValidationException("Phone number is already taken!");
            }
            String encodedPassword = cryptPasswordEncoder.encode(user.getPasswordHash());
            user.setPasswordHash(encodedPassword);
            userRepository.save(user);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ValidationException("An unexpected error occurred during validation.", e);
        }
    }

    public User getUserById(String userId){
        return userRepository.getUserById(userId);
    }

    public UserDTO getUserDTOById(String id) throws DataChangeException {
        User user = userRepository.getUserById(id);
        if(user == null){
            throw new DataChangeException("There is no user with this id!");
        }
        return toUserDTO(user);
    }

    public UserDTO getUserDTOByEmail(String email) throws DataChangeException {
        User user = userRepository.getUserByEmail(email);
        if(user == null){
            throw new DataChangeException("There is no user with this id!");
        }
        return toUserDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) userRepository.getUserByEmail(email);
    }
}
