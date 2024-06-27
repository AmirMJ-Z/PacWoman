package view.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.TalkingPacMan;
import view.GameLauncher;
import view.MainPage;

import java.sql.Time;

public class TalkingPacManAnimation extends Transition {
    private MainPage mainpage = null;
    private double VELOCITY = 5;
    private final double DURATION = 100.0;
    public TalkingPacManAnimation(MainPage mainPage, int forward) {
        this.mainpage = mainPage;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(DURATION));
        VELOCITY = VELOCITY * forward;
    }

    @Override
    protected void interpolate(double frac) {
        double x = mainpage.getTalkingPacMan().getX() - VELOCITY;

        if (x <= 970 - mainpage.getTalkingPacMan().WIDTH && VELOCITY > 0) {
            x = 970 - mainpage.getTalkingPacMan().WIDTH;
            mainpage.getTalkingPacMan().setX(x);
            stop();
        }

        else if (x >= 1000 && VELOCITY < 0) {
            x = 1000;
            mainpage.getTalkingPacMan().setX(x);
            stop();
        }

        mainpage.getTalkingPacMan().setX(x);
    }
}
