package model;
import java.sql.*;

public class Database {
    private String address;
    private Connection connection;
    private Statement statement;

    Database(String address) throws SQLException {
        this.address = address;
        connection = DriverManager.getConnection(address);
        statement = connection.createStatement();
    }

    public User getUserByUsername(String username) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "';");
        if (!resultSet.next()) {
            return null;
        }
        resultSet = statement.executeQuery("SELECT * FROM users WHERE username = '" + username + "';");
        return new User(username, Integer.parseInt(resultSet.getString("score")));
    }

    public void addUser(User user) throws SQLException {
        statement.execute("INSERT INTO users values ('" + user.getUsername() + "', " + user.getScore() + ");");
    }

    public void updateScore(User user, int score) throws SQLException {
        statement.execute("UPDATE users SET score = " + score + " WHERE username = '" + user.getUsername() + "';");
    }

}
