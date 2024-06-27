package view.animations.GhostAlgorithms;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.*;
import view.GameLauncher;

public class GeniusGhostAnimation extends Transition {
    private GameLauncher gameLauncher;
    private final double VELOCITY = 1;
    private final int DURATION = 100;
    private Ghost ghost;
    private final double DISTANCE = 20;

    private Line line;
    private final double MARGIN = 8;

    public GeniusGhostAnimation(GameLauncher gameLauncher, Ghost ghost) {
        this.ghost = ghost;
        this.gameLauncher = gameLauncher;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(DURATION));

        line = new Line(new Point(ghost.getX() + ghost.getWidth()/2, ghost.getY() + ghost.getHeight()/2), new Point(gameLauncher.getPacMan().getX() + gameLauncher.getPacMan().getWidth()/2, gameLauncher.getPacMan().getY() + gameLauncher.getPacMan().getHeight()/2));
    }

    @Override
    protected void interpolate(double frac) {
        double x=0, y=0;

        line = new Line(new Point(ghost.getX() + ghost.getWidth()/2, ghost.getY() + ghost.getHeight()/2), new Point(gameLauncher.getPacMan().getX() + gameLauncher.getPacMan().getWidth()/2, gameLauncher.getPacMan().getY() + gameLauncher.getPacMan().getHeight()/2));

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

        if (Math.abs(ghost.getY() - gameLauncher.getPacMan().getY()) < MARGIN) {
            if (gameLauncher.getPacMan().getX() > ghost.getX()) {
                if (!ghost.isRight()) {
                    ghost.turnRight();
                }
            }

            else {
                if (!ghost.isLeft()) {
                    ghost.turnLeft();
                }
            }
        }

        else if (Math.abs(ghost.getX() - gameLauncher.getPacMan().getX()) < MARGIN) {
            if (gameLauncher.getPacMan().getY() > ghost.getY()) {
                if (!ghost.isDown()) {
                    ghost.turnDown();
                }
            }

            else {
                if (!ghost.isUp()) {
                    ghost.turnUp();
                }
            }
        }

        else {
            if (line.getRegion() == Region.A) {
                if (!ghost.collision(XY.Y, -1)) {
                    ghost.turnUp();
                }

                else {
                    ghost.turnRight();
                }
            }

            else if (line.getRegion() == Region.B) {
                if (!ghost.collision(XY.Y, -1)) {
                    ghost.turnUp();
                }

                else {
                    ghost.turnLeft();
                }
            }

            else if (line.getRegion() == Region.C) {
                if (!ghost.collision(XY.Y, 1)) {
                    ghost.turnDown();
                }

                else {
                    ghost.turnLeft();
                }
            }

            else {
                if (!ghost.collision(XY.Y, 1)) {
                    ghost.turnDown();
                }

                else {
                    ghost.turnRight();
                }
            }
        }

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

        ghost.setX(x);
        ghost.setY(y);
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
