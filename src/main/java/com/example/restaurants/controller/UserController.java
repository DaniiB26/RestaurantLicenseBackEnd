package com.example.restaurants.controller;

import com.example.restaurants.controller.requestClasses.UserDTO;
import com.example.restaurants.exceptions.DataChangeException;
import com.example.restaurants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // Permite accesul doar de la frontend
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            UserDTO user = userService.getUserDTOByEmail(email);
            return ResponseEntity.ok(user);
        }catch (DataChangeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
