package model;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class StinkySocks extends Rectangle {
    public final double WIDTH = 33.0;
    public final double HEIGHT = 33.0;

    private final int score = 10;

    private Random random;
    private Game game;

    public int getScore() {
        return score;
    }
    public StinkySocks(Game game) {
        super(33, 33);
        random = new Random();
        this.game = game;
        this.setFill(new ImagePattern(new Image(getClass().getResource("/Images/StinkySocks.png").toExternalForm())));
        this.setEffect(new DropShadow(5, Color.GREEN));
        this.setRotate(random.nextInt(360));
    }

}
