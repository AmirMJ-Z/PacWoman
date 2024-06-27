package view.animations;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Ghost;
import model.Point;
import model.XY;
import view.GameLauncher;

import java.util.Random;

public class GhostAnimation extends Transition {
    private GameLauncher gameLauncher;
    private final double VELOCITY = 2;
    private final int DURATION = 100;
    private Ghost ghost;
    private Random random;
    private final double DISTANCE = 20;

    public GhostAnimation(GameLauncher gameLauncher, Ghost ghost) {
        random = new Random();
        this.ghost = ghost;
        this.gameLauncher = gameLauncher;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(DURATION));
    }
    @Override
    protected void interpolate(double frac) {
        double x=0, y=0;

        try {
            pacManIntersection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (ghost.getRotate() == 0) {
            x = ghost.getX() + VELOCITY;
            y = ghost.getY();
        }

        else if (ghost.getRotate() == 180) {
            x = ghost.getX() - VELOCITY;
            y = ghost.getY();
        }

        else if (ghost.getRotate() == 90) {
            y = ghost.getY() + VELOCITY;
            x = ghost.getX();
        }

        else if (ghost.getRotate() == 270) {
            y = ghost.getY() - VELOCITY;
            x = ghost.getX();
        }


        if (x <= 152) {
            x = 152;
            ghost.turnRight();
        }

        else if (x >= 848-ghost.getWidth()) {
            x = 848-ghost.getWidth();
            ghost.turnLeft();
        }

        if (y <= 0) {
            y = 0;
            ghost.turnDown();
        }

        if (y >= 1000-ghost.getHeight()) {
            y = 1000-ghost.getHeight();
            ghost.turnUp();
        }

        if (frac % 1000000000 == 0) {
            setRandomDirection();
        }

        ghost.setX(x);
        ghost.setY(y);

    }

    private void setRandomDirection() {
        int rand = random.nextInt(10);

        if (rand == 1) {
            if (!ghost.collision(XY.X, 1)) {
                ghost.turnRight();
            }
        }

        else if (rand == 3) {
            if (!ghost.collision(XY.Y, 1)) {
                ghost.turnDown();
            }
        }

        else if (rand == 5) {
            if (!ghost.collision(XY.Y, -1)) {
                ghost.turnUp();
            }
        }

        else if (rand == 7) {
            if (!ghost.collision(XY.X, -1)) {
                ghost.turnLeft();
            }
        }
    }

    private void pacManIntersection() throws Exception {
        Point pacmanPoint = new Point(gameLauncher.getPacMan().getX() + gameLauncher.getPacMan().WIDTH/2, gameLauncher.getPacMan().getY() + gameLauncher.getPacMan().HEIGHT/2);
        Point ghostPoint = new Point(ghost.getX() + ghost.getWidth()/2, ghost.getY() + ghost.getHeight()/2);

        if (Point.getDistance(pacmanPoint, ghostPoint) < DISTANCE) {
            stop();
            gameLauncher.getPane().getChildren().remove(gameLauncher.getPacMan());
            gameLauncher.getPane().getChildren().remove(gameLauncher.getGhosts());
            gameLauncher.getGame().gameLose();
        }
    }
}
