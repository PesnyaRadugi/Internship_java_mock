package com.internship.Mock;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.Date;
import java.util.Map;

@RestController
public class RestMock {

    private final UserRepository userRepository;

    public RestMock(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/get-json")
    public ResponseEntity<?> getStaticJSON() throws InterruptedException {
        String staticJSON = "{\"message1\": \"Hey!\",\"message2\": \"Dude!\"}";

        Thread.sleep(1000 + (long)(Math.random() * 1000));
        return ResponseEntity.ok(staticJSON);
    }

    @GetMapping("/get-json-object")
    public ResponseEntity<?> getObjectJSON() throws InterruptedException {
        User createdUser = new User("Kekw", "lol");


        Thread.sleep(1000 + (long)(Math.random() * 1000));
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/send-json")
    public ResponseEntity<?> sendJSON(@RequestBody Map<String, Object> request) throws InterruptedException {

        // Return status code 400
        if (!request.containsKey("login") || !request.containsKey("password") || request.size() != 2) {
            return ResponseEntity.badRequest().body("Request must contain only 'login' and 'password' fields");
        }

        request.put("date", new Date().toString());

        Thread.sleep(1000 + (long)(Math.random() * 1000));
        return ResponseEntity.ok(request);
    }

    @PostMapping("/send-json-object")
    public  ResponseEntity<?> sendObjectJSON(@Valid @RequestBody User user) throws InterruptedException {
        User createdUser = new User(user.login, user.password);

        Thread.sleep(1000 + (long)(Math.random() * 1000));
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/user/{login}")
    public ResponseEntity<?> getUser(@PathVariable String login) {
        try {
            return ResponseEntity.ok(userRepository.selectUserByLogin(login));
        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving user: " + e.getMessage());
        }
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
