package com.internship.Mock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class User {

    @NotBlank(message = "Login can't be empty")
    public String login;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 3)
    public String password;

    public Date date;

    public User() {

    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.date = new Date();
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

}
