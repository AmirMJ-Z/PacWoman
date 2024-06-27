package controller;

import model.App;
import model.User;

import java.sql.SQLException;

public class SignUpMenuController {

    public void login(User user) {
        App.app.setCurrentPlayer(user);
    }
    public void register(String username) throws SQLException {
        User user = new User(username);
        App.app.getDatabase().addUser(user);
        login(user);
    }
}
