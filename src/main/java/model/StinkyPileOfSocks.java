package model;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class StinkyPileOfSocks extends Rectangle {

    public final double WIDTH = 58.0;
    public final double HEIGHT = 58.0;
    private final int score = 50;

    private Random random;
    private Game game;

    public int getScore() {
        return score;
    }
    public StinkyPileOfSocks(Game game) {
        super(58, 58);
        random = new Random();
        this.game = game;
        this.setFill(new ImagePattern(new Image(getClass().getResource("/Images/StinkyPileOfSocks.png").toExternalForm())));
        this.setEffect(new DropShadow(5, Color.GREEN));
    }
}
