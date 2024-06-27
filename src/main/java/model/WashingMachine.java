package model;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import view.GameLauncher;

import java.util.Random;

public class WashingMachine extends Rectangle {

    public final double WIDTH = 200.0;
    public final double HEIGHT = 200.0;
    private GameLauncher gameLauncher;

    public WashingMachine(GameLauncher gameLauncher) {
        super(200.0, 200.0);
        this.gameLauncher = gameLauncher;
        this.setFill(new ImagePattern(new Image(getClass().getResource("/Images/WashingMachine.png").toExternalForm())));
        this.setEffect(new DropShadow(8, Color.GREEN));
        this.setX(1000 - WIDTH + 23);
        this.setY(1000 - HEIGHT);
    }

}
