package com.internship.Mock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {

    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;

    public User selectUserByLogin(String login) throws SQLException {
        User user = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT u.login, u.password, u.date, ue.email FROM users u " +
                "JOIN user_emails ue ON u.login = ue.login WHERE u.login = '" + login + "'";

        try {
            connection = DriverManager.getConnection(url, this.user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                user = new User(resultSet.getString("login"), resultSet.getString("password"),
                        resultSet.getString("date"), resultSet.getString("email"));
            } else {
                throw new SQLException("User " + login + " not found");
            }
        } catch (RuntimeException e) {
            System.err.println("An error occurred on creating request resources: " + e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return user;
    }

    public int insertUser(User user) throws SQLException{
        String query = "INSERT INTO users (login, password, date) VALUES (?, ?, ?);" +
                "INSERT INTO user_emails (login, email) VALUES (?, ?);";

        try (Connection connection = DriverManager.getConnection(url, this.user, password);
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
