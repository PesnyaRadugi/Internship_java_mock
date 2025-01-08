package com.internship.Mock;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestMock {

    @GetMapping("/get-json")
    public ResponseEntity<String> getStaticJSON() throws InterruptedException {
        Thread.sleep(1000 + (long)(Math.random() * 1000));
        String staticJSON = "{\"message\": \"Hey!\"}";
        return ResponseEntity.ok(staticJSON);
    }
}
