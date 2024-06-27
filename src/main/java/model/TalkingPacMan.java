package model;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.events.MouseEvent;
import view.GameLauncher;
import view.MainPage;
import view.animations.TalkingPacManAnimation;

public class TalkingPacMan extends Rectangle {
    public final double WIDTH = 300.0;
    public final double HEIGHT = 300.0;
    private MainPage mainPage = null;

    public TalkingPacMan(MainPage mainPage) {
        super(300.0, 300.0);
        this.mainPage = mainPage;
        this.setFill(new ImagePattern(new Image(getClass().getResource("/Images/TalkingPacman.png").toExternalForm())));
        this.setX(1000);
        this.setY(1000-HEIGHT);
        this.setEffect(new DropShadow(30, Color.YELLOW));
        setBehavior();
    }

    private void setBehavior() {this.setOnMouseEntered(MouseEvent -> {
        this.setWidth(this.getWidth()+100);
        this.setHeight(this.getHeight()+100);
        this.setX(this.getX()-100);
        this.setY(this.getY()-100);
    });
        this.setOnMouseExited(MouseEvent -> {
            this.setWidth(this.getWidth()-100);
            this.setHeight(this.getHeight()-100);
            this.setX(this.getX()+100);
            this.setY(this.getY()+100);
        });

        this.setOnMouseClicked(MouseEvent -> {
            TalkingPacManAnimation talkingPacManAnimation;
            talkingPacManAnimation = new TalkingPacManAnimation(mainPage, -1);

            talkingPacManAnimation.play();
        });
    }
}
