package view.animations;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class FadeInAnimation extends Transition {

    private BorderPane borderPane = new BorderPane();
    private final double DURATION = 800;
    public FadeInAnimation(BorderPane borderPane) {
        this.borderPane = borderPane;
        for (Node node : borderPane.getChildren()) {
            node.setOpacity(0);
        }
        this.setCycleCount(1);
        this.setCycleDuration(Duration.millis(50));
    }
    @Override
    protected void interpolate(double frac) {
        FadeTransition fadeTransition  = new FadeTransition();
        for (Node node : borderPane.getChildren()) {
            fadeTransition.setNode(node);
        }
        fadeTransition.setDuration(Duration.millis(DURATION));
        fadeTransition.setFromValue(0.01);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
