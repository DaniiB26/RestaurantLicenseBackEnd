package com.example.restaurants.validation;

import com.example.restaurants.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;
import java.util.regex.Pattern;

@AllArgsConstructor
@Component
public class ValidateUser {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,13}$");

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    public void validateUser(User user) throws ValidationException {
        if (!isValidEmail(user.getEmail())) {
            throw new ValidationException("Invalid email format!");
        }
        if (!isValidPhoneNumber(user.getPhoneNumber())) {
            throw new ValidationException("Invalid phone number format!");
        }
        if (!isValidPassword(user.getPasswordHash())) {
            throw new ValidationException("Password must contain at least 1 uppercase letter, at least one number, and be at least 8 characters long!");
        }
    }
}

