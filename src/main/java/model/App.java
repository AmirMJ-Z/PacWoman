package model;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.sql.SQLException;

public class App {
    private final double HIGH_VOLUME = 70 * 0.01;
    private final double LOW_VOLUME = 35 * 0.01;
    private final Database database = new Database("jdbc:sqlite:C:\\Users\\Amirr\\Main Server\\Java Projects\\PacMan\\src\\main\\resources\\Database\\usersdb.db");

    public Database getDatabase() {
        return database;
    }
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public double getWIDTH() {
        return WIDTH;
    }

    public double getHEIGHT() {
        return HEIGHT;
    }
    public static App app;

    Media theme_song = new Media(getClass().getResource("/Audios/ThemeSong.mp3").toExternalForm());
    MediaPlayer mediaPlayer = new MediaPlayer(theme_song);

    Media error_sound = new Media(getClass().getResource("/Audios/BreakingPlate.mp3").toExternalForm());
    MediaPlayer error_mediaPlayer = new MediaPlayer(error_sound);

    static {
        try {
            app = new App();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private App() throws SQLException {
        mediaPlayer.setCycleCount(-1);
        increaseVolume();
        mediaPlayer.play();
        error_mediaPlayer.setCycleCount(1);
        error_mediaPlayer.setVolume(HIGH_VOLUME);
    };

    public void playErrorSound() {
        error_mediaPlayer = new MediaPlayer(error_sound);
        error_mediaPlayer.setCycleCount(1);
        error_mediaPlayer.play();
    }

    public void reduceVolume() {
        mediaPlayer.setVolume(LOW_VOLUME);
    }

    public void increaseVolume() {
        mediaPlayer.setVolume(HIGH_VOLUME);
    }
    private Stage stage;
    private final double WIDTH = 1000;
    private final double HEIGHT = 1000;

    private User currentPlayer;

    public User getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(User currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void addWarningGraphic(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/CSS/AlertStyle.css").toString());
        dialogPane.getStyleClass().add("dialog");
        alert.setResizable(false);
        Image image = new Image(getClass().getResource("/Images/BrokenDish.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        alert.setGraphic(imageView);
    }

    public void addConfirmationGraphic(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/CSS/ConfirmationStyle.css").toString());
        dialogPane.getStyleClass().add("dialog");
        alert.setResizable(false);
        Image image = new Image(getClass().getResource("/Images/ConfirmationIcon.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        alert.setGraphic(imageView);
    }
}
