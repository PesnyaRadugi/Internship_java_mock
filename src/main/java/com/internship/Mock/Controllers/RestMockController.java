package com.internship.Mock.Controllers;

import com.internship.Mock.Models.User;
import com.internship.Mock.UserAlreadyExistsException;
import com.internship.Mock.Repositories.UserRepository;
import com.internship.Mock.Services.UsersInFile;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.Map;

@RestController
public class RestMockController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UsersInFile usersInFile;

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

    //Files writing/reading
    @GetMapping("/file/save/{login}")
    public ResponseEntity<?> addUserToFile(@PathVariable String login) {
        try {
            User user = userRepository.selectUserByLogin(login);
            usersInFile.addUser(user);
            return ResponseEntity.ok("User " + login + " was added to file");
        } catch (SQLException | UserAlreadyExistsException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/file/randomUser")
    public ResponseEntity<?> getRandomUserFromFile() {
        try {
            User randomUser = usersInFile.getRandomUser();
            return ResponseEntity.ok(randomUser);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
