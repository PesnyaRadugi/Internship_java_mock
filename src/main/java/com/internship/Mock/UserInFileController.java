package com.internship.Mock;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/file")
public class UserInFileController {
//    private final UsersInFile usersInFile = new UsersInFile();

//    @GetMapping("/save/{login}")
//    public ResponseEntity<?> addUserToFile(@PathVariable String login) {
//        try {
//            usersInFile.addByLogin(login);
//            return ResponseEntity.ok("User " + login + " was added to file");
//        } catch (SQLException e) {
//            return ResponseEntity.internalServerError().body("User " + login + " not found!" + e.getMessage());
//        }
//    }

//    @GetMapping("/randomUser")
//    public ResponseEntity<?> getRandomUserFromFile() {
//        try {
//            List<String> lines = Files.readAllLines(Path.of(file_path));
//            if (lines.isEmpty()) return ResponseEntity.internalServerError().body("File is empty: ");
//
//            String randomLine = lines.get(random.nextInt(lines.size()));
//            return ResponseEntity.ok(randomLine);
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().body("Error reading file: " + e.getMessage());
//        }
//    }
}
