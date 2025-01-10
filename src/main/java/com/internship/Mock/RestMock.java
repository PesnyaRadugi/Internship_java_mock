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
        String staticJSON = "{\"message1\": \"Hey!\",\"message2\": \"Dude!\"}";

        Thread.sleep(1000 + (long)(Math.random() * 1000));
        return ResponseEntity.ok(staticJSON);
    }

    @PostMapping("/send-json")
    public ResponseEntity<Map<String, Object>> sendJSON(@RequestBody Map<String, Object> request) throws InterruptedException {
        request.put("date", new Date());

        Thread.sleep(1000 + (long)(Math.random() * 1000));
        return ResponseEntity.ok(request);
    }
}
