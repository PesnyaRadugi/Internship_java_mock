package com.internship.Mock;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;

@RestController
public class RestMock {
    private static final String URL = "jdbc:postgresql://192.168.0.104:5432/exampledb";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

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
        String query = "SELECT u.login, u.password, u.date, ue.email " +
                "FROM users u JOIN user_emails ue ON u.login = ue.login " +
                "WHERE u.login = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    User user = new User(resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("date"),
                            resultSet.getString("email"));

                    return ResponseEntity.ok(user);
                } else {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error trying to get user: " + e.getMessage());
        }
    }
 }
