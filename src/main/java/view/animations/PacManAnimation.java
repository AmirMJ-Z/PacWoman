package view.animations;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.*;
import view.GameLauncher;

import java.util.ArrayList;

public class PacManAnimation extends Transition {
    private GameLauncher gameLauncher;
    private double VELOCITY = 1.5;
    private final int DURATION = 100;
    private boolean closed;
    private ImagePattern close;
    private ImagePattern open;
    private ImagePattern openFlipped;
    private ImagePattern flippedClosed;
    private final double DISTANCE = 20 ;
    private Countdown countdown;
    private Countdown speedyCountdown;

    public PacManAnimation(GameLauncher gameLauncher) {
        speedyCountdown = new Countdown(10);
        speedyCountdown.start();

        this.gameLauncher = gameLauncher;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(DURATION));
        this.closed = false;
        close = new ImagePattern(new Image(getClass().getResource("/Images/PacMan-Closed.png").toExternalForm()));
        open = new ImagePattern(new Image(getClass().getResource("/Images/pacman.png").toExternalForm()));
        flippedClosed = new ImagePattern(new Image(getClass().getResource("/Images/PacMan-Closed-Flipped.png").toExternalForm()));
        openFlipped = new ImagePattern(new Image(getClass().getResource("/Images/pacmanFlipped.png").toExternalForm()));
    }

    private void checkCountdown() {
        if (countdown != null && countdown.timeRemaining() == 0) {
            gameLauncher.resumeGhosts();
        }
    }

    private void checkSpeedies() {
        if (speedyCountdown != null && speedyCountdown.timeRemaining() == 0) {
            gameLauncher.removeSpeedySocks();
        }
    }
    @Override
    protected void interpolate(double frac) {
        checkCountdown();
        checkSpeedies();

        double x=0, y=0;
        if (gameLauncher.getPacMan().getRotate() == 0) {
            x = gameLauncher.getPacMan().getX() + VELOCITY;
            y = gameLauncher.getPacMan().getY();
        }

        else if (gameLauncher.getPacMan().getRotate() == 180) {
            x = gameLauncher.getPacMan().getX() - VELOCITY;
            y = gameLauncher.getPacMan().getY();
        }

        else if (gameLauncher.getPacMan().getRotate() == 90) {
            y = gameLauncher.getPacMan().getY() + VELOCITY;
            x = gameLauncher.getPacMan().getX();
        }

        else if (gameLauncher.getPacMan().getRotate() == 270) {
            y = gameLauncher.getPacMan().getY() - VELOCITY;
            x = gameLauncher.getPacMan().getX();
        }


        if (x <= 152) {
            x = 152;
        }

        else if (x >= 848-gameLauncher.getPacMan().getWidth()) {
            x = 848-gameLauncher.getPacMan().getWidth();
        }

        if (y <= 0) {
            y = 0;
        }

        if (y >= 1000-gameLauncher.getPacMan().getHeight()) {
            y = 1000-gameLauncher.getPacMan().getHeight();
        }

        if (frac % 5 == 0) {
            if (closed) {
                if (gameLauncher.getPacMan().isLeft()) {
                    gameLauncher.getPacMan().setFill(openFlipped);
                }

                else {
                    gameLauncher.getPacMan().setFill(open);
                }
                closed = false;
            }

            else {
                if (gameLauncher.getPacMan().isLeft()) {
                    gameLauncher.getPacMan().setFill(flippedClosed);
                }

                else {
                    gameLauncher.getPacMan().setFill(close);
                }
                closed = true;
            }
        }

        try {
            intersect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        gameLauncher.getPacMan().setX(x);
        gameLauncher.getPacMan().setY(y);
    }

    private void intersect() throws Exception {
        double offset;

        if (gameLauncher.getSocks().getChildren().isEmpty()) {
            stop();
            gameLauncher.getGame().gameWin();
            return;
        }

        ArrayList<Rectangle> allSocks = new ArrayList<>();

        for (Node i : gameLauncher.getSocks().getChildren()) {
            allSocks.add((Rectangle) i);
        }

        for (Node i : gameLauncher.getSpeedySocks().getChildren()) {
            allSocks.add((Rectangle) i);
        }

        for (int i=0; i< allSocks.size(); i++) {
            offset = 0;

            Node node = allSocks.get(i);
            Rectangle sock = (Rectangle) node;
            Point pacManPoint = new Point(gameLauncher.getPacMan().getX() + gameLauncher.getPacMan().getWidth()/2, gameLauncher.getPacMan().getY() + gameLauncher.getPacMan().getHeight()/2);
            Point socksPoint = new Point(sock.getX() + sock.getWidth()/2, sock.getY() + sock.getHeight()/2);

            if (gameLauncher.getPacMan().getY() == 0 || gameLauncher.getPacMan().getY() == 1000 - gameLauncher.getPacMan().getHeight() || gameLauncher.getPacMan().getX() == 152 || gameLauncher.getPacMan().getX() == 848 - gameLauncher.getPacMan().getWidth()) {
                offset = 15;
            }

            if (Point.getDistance(pacManPoint, socksPoint) < DISTANCE + offset) {
                gameLauncher.getSocks().getChildren().remove(node);
                gameLauncher.getSpeedySocks().getChildren().remove(node);
                gameLauncher.getGame().playPop();

                if (node.getClass() == StinkySocks.class) {
                    gameLauncher.getGame().addScore(((StinkySocks) node).getScore());
                    FallingSockAnimation fallingSockAnimation = new FallingSockAnimation(gameLauncher);
                    fallingSockAnimation.play();
                }

                else if (node.getClass() == StinkyPileOfSocks.class) {
                    gameLauncher.getGame().addScore(((StinkyPileOfSocks) node).getScore());
                    FallingSockAnimation fallingSockAnimation = new FallingSockAnimation(gameLauncher);
                    fallingSockAnimation.play();
                }

                else if (node.getClass() == FrozenSocks.class) {
                    countdown = new Countdown(5);
                    countdown.start();
                    gameLauncher.pauseGhosts();
                }

                else {
                    VELOCITY += 0.15;
                }
            }
        }

    }
}
