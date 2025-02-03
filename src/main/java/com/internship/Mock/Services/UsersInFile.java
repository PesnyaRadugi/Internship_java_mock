package com.internship.Mock.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.Mock.Models.User;
import com.internship.Mock.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@Service
public class UsersInFile {
    private final String file_path = "users.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    public void addUser(User user) throws SQLException, UserAlreadyExistsException {
        try {
            String jsonLine = objectMapper.writeValueAsString(user);
            Files.writeString(Path.of(file_path), jsonLine + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
        }

    }

    public User getRandomUser() {
        try {
            List<String> lines = Files.readAllLines(Path.of(file_path));
            String randomLine = lines.get(random.nextInt(lines.size()));
            return objectMapper.readValue(randomLine, User.class);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }
}
