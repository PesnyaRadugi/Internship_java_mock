package com.internship.Mock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class User {

    @NotBlank(message = "Login can't be empty")
    public String login;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    public String password;

    @NotBlank(message = "Date can't be empty")
    public String date;

    @NotBlank(message = "Email can't be empty")
    public String email;

    public User() {

    }

    public  User(String login, String password, String date, String email) {
        this.login = login;
        this.password = password;
        this.date = date;
        this.email = email;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.date = formatDate(ZonedDateTime.now());
    }

    @Override
    public String toString() {
        return String.format(
                "{\"login\":\"%s\"," +
                        "\"password\": \"%s\"," +
                        "\"date\": \"%s\"}",
                login, password, date
        );
    }

    private String formatDate(ZonedDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ZonedDateTime moscowTime = dateTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        return moscowTime.format(formatter);
    }

}
