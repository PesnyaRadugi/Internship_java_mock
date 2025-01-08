package com.internship.Mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MockApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockApplication.class, args);
	}

}
