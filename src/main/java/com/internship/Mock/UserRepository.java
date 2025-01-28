package com.internship.Mock;

import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {

    private static final String URL = "jdbc:postgresql://192.168.0.104:5432/exampledb";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public User selectUserByLogin(String login) throws SQLException {
        String query = "SELECT u.login, u.password, u.date, ue.email FROM users u " +
                "JOIN user_emails ue ON u.login = ue.login WHERE u.login = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getString("login"), resultSet.getString("password"),
                            resultSet.getString("date"), resultSet.getString("email"));
                } else {
                    throw new SQLException("User not found");
                }
            }
        }
    }

    public int insertUser(User user) throws SQLException{
        String query = "INSERT INTO users (login, password, date) VALUES (?, ?, ?);" +
                "INSERT INTO user_emails (login, email) VALUES (?, ?);";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            //users
            statement.setString(1, user.login);
            statement.setString(2, user.password);
            statement.setTimestamp(3, Timestamp.valueOf(user.date));

            //user_emails
            statement.setString(4, user.login);
            statement.setString(5, user.email);

            return statement.executeUpdate();
        }
    }
}
