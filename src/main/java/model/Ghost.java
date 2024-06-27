package model;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Ghost extends Rectangle {

    public static final double WIDTH = 25.0;
    public static final double HEIGHT = 25.0;
    private final double HORIZONTAL_OFFSET = 65;
    private final double VERTICAL_OFFSET = 65;
    private Game game;
    private ImagePattern texture;
    private ImagePattern flippedTexture;

    public Ghost(Game game, double x, double y, Image t1, Image t2) {
        super(25, 25);
        this.game = game;
        this.setX(x);
        this.setY(y);
        texture = new ImagePattern(t1);
        flippedTexture = new ImagePattern(t2);
        this.setFill(texture);
        this.setEffect(new DropShadow(15, Color.BLACK));
    }

    public Ghost(Game game, double x, double y) {
        super(25, 25);
        this.game = game;
        this.setX(x);
        this.setY(y);
    }

    public void turnRight() {
        if (!collision(XY.X, 1)) {
            this.setFill(texture);
            this.setRotate(0);
        }

        else {
            turnUp();

            if (collision(XY.Y, -1)) {
                turnDown();
            }
        }
    }

    public void turnUp() {
        if (!collision(XY.Y, -1)) {
            this.setRotate(270);
        }

        else {
            turnRight();

            if (collision(XY.X, 1)) {
                turnLeft();
            }
        }
    }

    public void turnDown() {
        if (!collision(XY.Y, 1)) {
            this.setRotate(90);
        }

        else {
            turnRight();

            if (collision(XY.X, 1)) {
                turnLeft();
            }
        }
    }

    public void turnLeft() {
        if (!collision(XY.X, -1)) {
            this.setFill(flippedTexture);
            this.setRotate(180);
        }

        else {
            turnUp();

            if (collision(XY.Y, -1)) {
                turnDown();
            }
        }
    }

    public boolean collision(XY xy, int sign) {
        double offset;
        Ghost ghost;

        if ((this.getX() >= 700 || (this.getX() <= 270 && this.getX() > 0) && xy == XY.X)) {
            sign *= 1.5;
        }

        if (((this.getY() >= 0 && this.getY() <= 200) || (this.getY() >= 880 && this.getY() <= 1000)) && xy == XY.Y) {
            sign *= 1.5;
        }

        if (xy == XY.X) {
            offset = HORIZONTAL_OFFSET * sign;
            ghost = new Ghost(this.game, this.getX()+offset, this.getY());
        }

        else {
            offset = VERTICAL_OFFSET * sign;
            ghost = new Ghost(this.game, this.getX(), this.getY()+offset);
        }

        for (Node node : game.getGameLauncher().getCollisions().getChildren()) {
            Rectangle rectangle = (Rectangle) node;
            if (rectangle.getBoundsInParent().intersects(ghost.getBoundsInParent())) {
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
