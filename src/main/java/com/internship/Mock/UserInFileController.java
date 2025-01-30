package com.internship.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/file")
public class UserInFileController {
    private final String file_path = "users.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();
    private final UserRepository userRepository = new UserRepository();

    @GetMapping("/save/{login}")
    public ResponseEntity<?> addUserToFile(@PathVariable String login) {
        try {
            User user = userRepository.selectUserByLogin(login);
            Set<String> existingUsers = new HashSet<>(Files.readAllLines(Path.of(file_path)));
            String jsonLine = objectMapper.writeValueAsString(user);

            if (existingUsers.contains(jsonLine)) {
                return ResponseEntity.badRequest().body("User " + login + " was already added!");
            }

            Files.writeString(Path.of(file_path), jsonLine + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return ResponseEntity.ok("User " + login + " saved to file");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Error retrieving user: " + e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body("Error processing user data: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error writing to file: " + e.getMessage());
        }
    }

    @GetMapping("/randomUser")
    public ResponseEntity<?> getRandomUserFromFile() {
        try {
            List<String> lines = Files.readAllLines(Path.of(file_path));
            if (lines.isEmpty()) return ResponseEntity.internalServerError().body("File is empty: ");

            String randomLine = lines.get(random.nextInt(lines.size()));
            return ResponseEntity.ok(randomLine);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error reading file: " + e.getMessage());
        }
    }
}
