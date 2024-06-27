package view.animations;

import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Point;
import model.StinkyPileOfSocks;
import model.StinkySocks;
import view.GameLauncher;

public class PacManAnimation extends Transition {
    private GameLauncher gameLauncher;
    private final double VELOCITY = 1.5;
    private final int DURATION = 100;
    private boolean closed;
    private ImagePattern close;
    private ImagePattern open;
    private ImagePattern openFlipped;
    private ImagePattern flippedClosed;
    private final double DISTANCE = 20 ;

    public PacManAnimation(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(DURATION));
        this.closed = false;
        close = new ImagePattern(new Image(getClass().getResource("/Images/PacMan-Closed.png").toExternalForm()));
        open = new ImagePattern(new Image(getClass().getResource("/Images/pacman.png").toExternalForm()));
        flippedClosed = new ImagePattern(new Image(getClass().getResource("/Images/PacMan-Closed-Flipped.png").toExternalForm()));
        openFlipped = new ImagePattern(new Image(getClass().getResource("/Images/pacmanFlipped.png").toExternalForm()));
    }
    @Override
    protected void interpolate(double frac) {
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

        for (int i=0; i<gameLauncher.getSocks().getChildren().size(); i++) {
            offset = 0;

            Node node = gameLauncher.getSocks().getChildren().get(i);
            Rectangle sock = (Rectangle) node;
            Point pacManPoint = new Point(gameLauncher.getPacMan().getX() + gameLauncher.getPacMan().getWidth()/2, gameLauncher.getPacMan().getY() + gameLauncher.getPacMan().getHeight()/2);
            Point socksPoint = new Point(sock.getX() + sock.getWidth()/2, sock.getY() + sock.getHeight()/2);

            if (gameLauncher.getPacMan().getY() == 0 || gameLauncher.getPacMan().getY() == 1000 - gameLauncher.getPacMan().getHeight() || gameLauncher.getPacMan().getX() == 152 || gameLauncher.getPacMan().getX() == 848 - gameLauncher.getPacMan().getWidth()) {
                offset = 15;
            }

            if (Point.getDistance(pacManPoint, socksPoint) < DISTANCE + offset) {
                gameLauncher.getSocks().getChildren().remove(node);
                gameLauncher.getGame().playPop();

                if (node.getClass() == StinkySocks.class) {
                    gameLauncher.getGame().addScore(((StinkySocks) node).getScore());
                    FallingSockAnimation fallingSockAnimation = new FallingSockAnimation(gameLauncher);
                    fallingSockAnimation.play();
                }

                else {
                    gameLauncher.getGame().addScore(((StinkyPileOfSocks) node).getScore());
                    FallingSockAnimation fallingSockAnimation = new FallingSockAnimation(gameLauncher);
                    fallingSockAnimation.play();
                }
            }
        }

    }
}
