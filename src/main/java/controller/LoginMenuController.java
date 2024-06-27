package controller;

import model.App;

import java.sql.SQLException;

public class LoginMenuController {
    public void login(String username) throws SQLException {
        App.app.setCurrentPlayer(App.app.getDatabase().getUserByUsername(username));
    }
}
