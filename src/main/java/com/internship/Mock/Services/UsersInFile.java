package com.internship.Mock.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.Mock.Models.User;
import com.internship.Mock.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Random;

@Service
public class UsersInFile {
    @Value("${file.users}")
    private String file_path;
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
        try (BufferedReader reader = Files.newBufferedReader(Path.of(file_path))) {
            int targetIndex = random.nextInt(10);
            String randomLine = null;

            for (int i = 0; i <= targetIndex; i++) randomLine = reader.readLine();

            if (randomLine == null) {
                throw new RuntimeException("Index out of bounds: " + (targetIndex + 1));
            }

            return objectMapper.readValue(randomLine, User.class);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }
}
