package com.internship.Mock;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.Map;

@RestController
public class RestMock {
    private final UserRepository userRepository;

    public RestMock(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{login}")
    public ResponseEntity<?> getUser(@PathVariable String login) {
        try {
            return ResponseEntity.ok(userRepository.selectUserByLogin(login));
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving user: " + e.getMessage());
        }
    }

    @PostMapping("/insertUserMap")
    public ResponseEntity<?> addUserMap (@RequestBody Map<?, ?> request) throws InterruptedException {

        // Return status code 400
        if (request.size() != 4 || !request.containsKey("login") || !request.containsKey("password")
            || !request.containsKey("date") || !request.containsKey("email")) {
            return ResponseEntity.badRequest().body("Incorrect JSON format");
        }

        User user = new User(request.get("login").toString(), request.get("password").toString(),
                            request.get("date").toString(), request.get("email").toString());

        try {
            int rowsInserted = userRepository.insertUser(user);
            return ResponseEntity.ok("Inserted " + rowsInserted + " rows.");
        } catch (SQLException e) {
            return ResponseEntity.badRequest().body("Couldn't insert user: " + e.getMessage());
        }
        // Thread.sleep(1000 + (long)(Math.random() * 1000));
    }

    @PostMapping("/insertUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        try {
            int rowsInserted = userRepository.insertUser(user);
            return ResponseEntity.ok("Inserted " + rowsInserted + " rows.");
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect data: " + e.getMessage());
        }
    }
}
