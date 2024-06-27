package view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.App;
import model.TalkingPacMan;
import model.User;
import view.animations.TalkingPacManAnimation;

import java.net.URL;
import java.sql.SQLException;

public class MainPage extends Application {
    private TalkingPacMan talkingPacMan;

    public TalkingPacMan getTalkingPacMan() {
        return talkingPacMan;
    }
    private BorderPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        App.app.setStage(stage);
        URL url = getClass().getResource("/FXML/MainFXML.fxml");
        root = FXMLLoader.load(url);
        initialize();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void initialize() {
        createTalkingPacman();
    }

    private void createTalkingPacman() {
        talkingPacMan = new TalkingPacMan(this);
        TalkingPacManAnimation talkingPacManAnimation = new TalkingPacManAnimation(this, 1);
        talkingPacManAnimation.play();
        root.getChildren().add(talkingPacMan);
    }
}