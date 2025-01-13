package com.internship.Mock;

import java.util.Date;

public class User {
    public String login;
    public  String password;
    public Date date;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.date = new Date();
    }

}
