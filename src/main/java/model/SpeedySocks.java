package model;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.MainPage;

import java.util.Random;

public class SpeedySocks extends Rectangle {
    public final double WIDTH = 33.0;
    public final double HEIGHT = 33.0;

    private Random random;
    private Game game;

    public SpeedySocks(Game game) {
        super(33, 33);
        random = new Random();
        this.game = game;
        this.setFill(new ImagePattern(new Image(MainPage.class.getResource("/Images/SpeedySocks.png").toExternalForm())));
        this.setEffect(new DropShadow(10, Color.RED));
        this.setRotate(random.nextInt(360));
    }
}
