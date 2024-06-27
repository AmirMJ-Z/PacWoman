package model;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class BigStinkySocks extends Rectangle {
    public final double WIDTH = 70;
    public final double HEIGHT = 70;

    private Random random;
    private Game game;
    public BigStinkySocks(Game game) {
        super(70, 70);
        random = new Random();
        this.game = game;
        this.setFill(new ImagePattern(new Image(getClass().getResource("/Images/StinkySocks.png").toExternalForm())));
        this.setEffect(new DropShadow(5, Color.GREEN));
        this.setRotate(random.nextInt(180));
    }
}
