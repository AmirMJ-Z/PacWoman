package model;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class PacMan extends Rectangle {
    public final double WIDTH = 25.0;
    public final double HEIGHT = 25.0;
    private final double HORIZONTAL_OFFSET = 65;
    private final double VERTICAL_OFFSET = 65;
    public Game game;
    private ImagePattern texture;
    private ImagePattern flippedTexture;
    public PacMan(Game game) {
        super(25.0, 25.0);
        this.game = game;
        setX(500.0);
        setY(500.0-10);
        texture = new ImagePattern(new Image(getClass().getResource("/Images/pacman.png").toExternalForm()));
        flippedTexture = new ImagePattern(new Image(getClass().getResource("/Images/pacmanFlipped.png").toExternalForm()));
        setFill(texture);
        this.setEffect(new DropShadow(15, Color.BLACK));
        initialize();
    }

    private void initialize() {
        this.setOnKeyPressed(KeyEvent -> {
            if (KeyEvent.getCode().equals(KeyCode.RIGHT)) {
                this.turnRight();
            }

            else if (KeyEvent.getCode().equals(KeyCode.LEFT)) {
                this.turnLeft();
            }

            else if (KeyEvent.getCode().equals(KeyCode.UP)) {
                this.turnUp();
            }

            else if (KeyEvent.getCode().equals(KeyCode.DOWN)) {
                this.turnDown();
            }
        });
    }

    private void turnRight() {
        if (!collision(XY.X, 1)) {
            this.setFill(texture);
            this.setRotate(0);
        }
    }

    private void turnUp() {
        if (!collision(XY.Y, -1)) {
            this.setRotate(270);
        }
    }

    private void turnDown() {
        if (!collision(XY.Y, 1)) {
            this.setRotate(90);
        }
    }

    private void turnLeft() {
        if (!collision(XY.X, -1)) {
            this.setFill(flippedTexture);
            this.setRotate(180);
        }
    }

    private boolean collision(XY xy, int sign) {
        double offset;
        PacMan pacMan;

        if ((this.getX() > 730 || (this.getX() < 270 && this.getX() > 150) && xy == XY.X)) {
            sign *= 1.5;
        }

        if (((this.getY() > 0 && this.getY() < 100) || (this.getY() > 900 && this.getY() < 1000)) && xy == XY.Y) {
            sign *= 1.5;
        }

        if (xy == XY.X) {
            offset = HORIZONTAL_OFFSET * sign;
            pacMan = new PacMan(this.game);
            pacMan.setX(this.getX() + offset);
            pacMan.setY(this.getY());
        }

        else {
            offset = VERTICAL_OFFSET * sign;
            pacMan = new PacMan(this.game);
            pacMan.setX(this.getX());
            pacMan.setY(this.getY() + offset);
        }

        for (Node node : game.getGameLauncher().getCollisions().getChildren()) {
            Rectangle rectangle = (Rectangle) node;
            if (rectangle.getBoundsInParent().intersects(pacMan.getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }

    public boolean isLeft() {
        if (this.getRotate() == 180) {
            return true;
        }

        return false;
    }

    public boolean isRight() {
        if (this.getRotate() == 0) {
            return true;
        }

        return false;
    }

    public boolean isUp() {
        if (this.getRotate() == 270) {
            return true;
        }

        return false;
    }

    public boolean isDown() {
        if (this.getRotate() == 90) {
            return true;
        }

        return false;
    }
}
