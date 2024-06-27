package view.animations;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.util.Duration;
import model.BigStinkySocks;
import model.StinkySocks;
import view.GameLauncher;

public class FallingSockAnimation extends Transition {
    private double VELOCITY = 0.5;
    private final double DURATION = 100;
    private final double ACCELERATION = 0.5;
    private GameLauncher gameLauncher;
    private BigStinkySocks stinkySocks;

    FallingSockAnimation(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(DURATION));
        this.stinkySocks = new BigStinkySocks(gameLauncher.getGame());
        this.stinkySocks.setX(923 - stinkySocks.getWidth()/2);
        this.stinkySocks.setY(-100);
        int index = gameLauncher.getPane().getChildren().indexOf(gameLauncher.getScoreVisualizer());
        gameLauncher.getPane().getChildren().add(index, stinkySocks);
    }

    @Override
    protected void interpolate(double frac) {

        double y = stinkySocks.getY() + VELOCITY;
        VELOCITY += ACCELERATION;
        stinkySocks.setRotate(stinkySocks.getRotate()+5);

        if (stinkySocks.getY() >= 800) {
            gameLauncher.getPane().getChildren().remove(stinkySocks);
            gameLauncher.updateScore();
            stop();
        }

        stinkySocks.setY(y);
    }
}
