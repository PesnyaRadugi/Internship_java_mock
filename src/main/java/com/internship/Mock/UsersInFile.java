package com.internship.Mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class UsersInFile {
    private final String file_path = "users.txt";
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    public UsersInFile(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addByLogin(String login) throws SQLException, UserAlreadyExistsException {
        User user = userRepository.selectUserByLogin(login);
        try {
            Set<String> existingUsers = new HashSet<>(Files.readAllLines(Path.of(file_path)));
            String jsonLine = objectMapper.writeValueAsString(user);

            if (existingUsers.contains(jsonLine)) {
                throw new UserAlreadyExistsException("User " + login + " was already added!");
            }

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
