package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.App;
import view.animations.FadeInAnimation;

import java.net.URL;

public class Login extends Application {
    BorderPane root;
    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("/FXML/LoginFXML.fxml");
        root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        initialize();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        FadeInAnimation fadeInAnimation = new FadeInAnimation(root);
        fadeInAnimation.play();
    }
}
