package com.internship.Mock;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestMock {

    @GetMapping("/get-json")
    public ResponseEntity<String> getStaticJSON() throws InterruptedException {
        Thread.sleep(1000 + (long)(Math.random() * 1000));

        String staticJSON = "{\"message\": \"Hey!\"}";
        return ResponseEntity.ok(staticJSON);
    }

    @PostMapping("/send-json")
    public ResponseEntity<Map<String, String>> sendJSON(@RequestBody Map<String, String> request) throws InterruptedException {
        Thread.sleep(1000 + (long)(Math.random() * 1000));

        Map<String, String> response = new HashMap<>();
        response.put("login", request.get("login"));
        response.put("password", request.get("password"));
        response.put("date", new Date().toString());
        return ResponseEntity.ok(response);
    }
}
