package view;

import controller.SignUpMenuController;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.App;

import java.sql.SQLException;

import static model.App.app;

public class SignupController {
    public Label welcomeText;
    public TextField usernameField;
    SignUpMenuController sign = new SignUpMenuController();

    public void updateLabel(KeyEvent keyEvent) {
        welcomeText.setText("Welcome " + usernameField.getText() + "!");
    }

    public void signup(MouseEvent mouseEvent) throws Exception {
        if (app.getDatabase().getUserByUsername(usernameField.getText()) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Signup Error");
            alert.setHeaderText(" ");
            alert.setContentText("Username " + usernameField.getText() + " already exists in the database, use the login button instead.");
            App.app.addWarningGraphic(alert);
            App.app.playErrorSound();
            alert.showAndWait();
            return;
        }

        sign.register(usernameField.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Signup Successful");
        alert.setHeaderText(" ");
        alert.setContentText("Username " + usernameField.getText() + " wasd registered into the database successfully.");
        App.app.addConfirmationGraphic(alert);
        alert.showAndWait();
        GameLauncher gameLauncher = new GameLauncher(App.app.getDatabase().getUserByUsername(usernameField.getText()));
        App.app.reduceVolume();
        gameLauncher.start(App.app.getStage());
    }

    public void back(KeyEvent keyEvent) throws Exception {
        if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
            MainPage mainPage = new MainPage();
            mainPage.start(App.app.getStage());
        }
    }

    public void submit(KeyEvent keyEvent) throws Exception {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (app.getDatabase().getUserByUsername(usernameField.getText()) != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Signup Error");
                alert.setHeaderText(" ");
                alert.setContentText("Username " + usernameField.getText() + " already exists in the database, use the login button instead.");
                App.app.addWarningGraphic(alert);
                App.app.playErrorSound();
                alert.showAndWait();
                return;
            }

            sign.register(usernameField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Signup Successful");
            alert.setHeaderText(" ");
            alert.setContentText("Username " + usernameField.getText() + " wasd registered into the database successfully.");
            App.app.addConfirmationGraphic(alert);
            alert.showAndWait();
            GameLauncher gameLauncher = new GameLauncher(App.app.getDatabase().getUserByUsername(usernameField.getText()));
            App.app.reduceVolume();
            gameLauncher.start(App.app.getStage());
        }
    }
}
