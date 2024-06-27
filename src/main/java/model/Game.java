package model;

import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.GameLauncher;
import view.MainPage;

public class Game {
    public Game(User user, GameLauncher gameLauncher) {
        this.currentPlayer = user;
        this.gameLauncher = gameLauncher;
        score = 0;
    }

    public GameLauncher getGameLauncher() {
        return gameLauncher;
    }
    private User currentPlayer;
    private GameLauncher gameLauncher;
    private int score;
    public final double WIDTH = 1000.0;
    public final double HEIGHT = 1000.0;

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return score;
    }

    private Media pop_soundeffect = new Media(getClass().getResource("/Audios/BubblePop.mp3").toExternalForm());
    private MediaPlayer mediaPlayer;

    public void gameWin() throws Exception {
        gameLauncher.stopGhostAnimations();

        App.app.getDatabase().updateScore(currentPlayer, Math.max(score, currentPlayer.getScore()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Win");
        alert.setHeaderText(" ");
        alert.setContentText("You won the game with the score " + score);
        App.app.addConfirmationGraphic(alert);
        alert.show();

        MainPage mainPage = new MainPage();
        App.app.increaseVolume();
        mainPage.start(App.app.getStage());
    }

    public void gameLose() throws Exception {
        gameLauncher.stopGhostAnimations();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("GAME OVER");
        alert.setHeaderText(" ");
        alert.setContentText("You lost the game with the score " + score);
        App.app.addWarningGraphic(alert);
        App.app.playErrorSound();
        alert.show();

        MainPage mainPage = new MainPage();
        App.app.increaseVolume();
        mainPage.start(App.app.getStage());
    }

    public void playPop() {
        mediaPlayer = new MediaPlayer(pop_soundeffect);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.setVolume(0.7);
        mediaPlayer.play();
    }
}
