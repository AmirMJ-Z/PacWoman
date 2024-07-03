package view;

import controller.GameController;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import view.animations.GhostAlgorithms.GhostAnimation;
import view.animations.GhostAlgorithms.GeniusGhostAnimation;
import view.animations.GhostAlgorithms.SemiGeniusGhostAnimation;
import view.animations.GhostAlgorithms.SmartGhostAnimation;

import java.util.ArrayList;
import java.util.Random;

public class GameLauncher extends Application {
    private Pane pane;
    private Pane scoreVisualizer = new Pane();
    private Text score = new Text();
    private Random random = new Random();
    private Pane collisions = new Pane();
    private Pane socks = new Pane();
    private Pane ghosts = new Pane();
    private Pane speedySocks = new Pane();

    public ArrayList<Transition> getGhostAnimations() {
        return ghostAnimations;
    }
    public ArrayList<Transition> getAllAnimations() {return allAnimations;}

    private ArrayList<Transition> allAnimations = new ArrayList<>();

    public Pane getGhosts() {
        return ghosts;
    }
    private ArrayList<Transition> ghostAnimations = new ArrayList<>();

    public Pane getSocks() {
        return socks;
    }

    public Pane getCollisions() {
        return collisions;
    }
    private Game game;
    private PacMan pacMan;

    public Pane getScoreVisualizer() {
        return scoreVisualizer;
    }
    private TalkingPacMan talkingPacMan;
    private controller.GameController gameController;

    public PacMan getPacMan() {
        return pacMan;
    }

    public Game getGame() {
        return game;
    }

    GameLauncher(User user) {
        pane = new Pane();
        game = new Game(user, this);
        pacMan = new PacMan(this.game);
        gameController = new GameController(this);
    }

    public Pane getSpeedySocks() {
        return speedySocks;
    }

    public TalkingPacMan getTalkingPacMan() {
        return talkingPacMan;
    }

    @Override
    public void start(Stage stage) throws Exception {
        setSize();
        initialization();
        Scene scene = new Scene(pane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        pacMan.requestFocus();
    }

    public void setSize() {
        pane.setMinWidth(App.app.getWIDTH());
        pane.setMaxWidth(App.app.getWIDTH());
        pane.setMinHeight(App.app.getHEIGHT());
        pane.setMaxHeight(App.app.getHEIGHT());
    }

    public void initialization() {
        createPacMan();
        setCollisions();
        socksInitialization();
        setScoreVisualizer();
        initializeGhosts();
        pane.setBackground(new Background(getBackground()));
    }

    public void createPacMan() {
        pane.getChildren().add(pacMan);
    }

    public BackgroundImage getBackground() {
        Image image = new Image(GameLauncher.class.getResource("/Images/GameBackground.jpg").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    private void setCollisions() {
        Rectangle rectangle;
        int width = 78 + 150;
        int height = 56;
        int w, h;
        for (int i=0; i<4; i++) {
            w = width + (i * 152);
            for (int j=0; j<6; j++) {
                h = height + (j * 160);
                rectangle = new Rectangle(88, 88);
                rectangle.setX(w);
                rectangle.setY(h);
                rectangle.setFill(Color.TRANSPARENT);
                collisions.getChildren().add(rectangle);
            }
        }

        pane.getChildren().add(collisions);
    }

    private void socksInitialization() {
        int width = 246 - 77 + 5;
        int height = 17;
        int x, y;

        for (int i=0; i<9; i++) {
            x = width + (i * 77);
            for (int j=0; j<7; j++) {
                y = height + (j * 158);
                if (!((i == 0 && (j == 0 || j == 6)) || (i == 8 && (j == 0 || j == 6)))) {
                    if (random.nextInt(20) == 9) {
                        StinkyPileOfSocks stinkyPileOfSocks = new StinkyPileOfSocks(game);
                        stinkyPileOfSocks.setX(x-10);
                        stinkyPileOfSocks.setY(y-24);
                        socks.getChildren().add(stinkyPileOfSocks);
                    }

                    else if (random.nextInt(25) == 9) {
                        SpeedySocks speedySock = new SpeedySocks(game);
                        speedySock.setX(x);
                        speedySock.setY(y);
                        speedySocks.getChildren().add(speedySock);
                    }

                    else if (random.nextInt(25) == 9) {
                        FrozenSocks frozenSocks = new FrozenSocks(game);
                        frozenSocks.setX(x);
                        frozenSocks.setY(y);
                        socks.getChildren().add(frozenSocks);
                    }

                    else {
                        StinkySocks stinkySock = new StinkySocks(game);
                        stinkySock.setX(x);
                        stinkySock.setY(y);
                        socks.getChildren().add(stinkySock);
                    }
                }
            }
        }

        for (int i=0; i<5; i++) {
            x = width + (i * 77 * 2);
            for (int j=0; j<6; j++) {
                y = height + (158/2) + (j * 158);

                StinkySocks stinkySock = new StinkySocks(game);
                stinkySock.setX(x);
                stinkySock.setY(y);
                socks.getChildren().add(stinkySock);
            }
        }

        int index = pane.getChildren().indexOf(pacMan);
        pane.getChildren().add(index, socks);
        pane.getChildren().add(index, speedySocks);
    }

    private void setScoreVisualizer() {
        WashingMachine washingMachine = new WashingMachine(this);
        scoreVisualizer.getChildren().add(washingMachine);
        score.setText("0");
        score.setFill(Color.rgb(98, 114, 102));
        score.setFont(new Font("Arial Rounded MT Bold", 25));
        score.setEffect(new DropShadow(2, Color.BLACK));
        score.setX(washingMachine.getX() + washingMachine.getWidth()/2 - score.getLayoutBounds().getWidth()/2);
        score.setY(washingMachine.getY() + washingMachine.getHeight()/2 + 15);
        scoreVisualizer.getChildren().add(score);
        pane.getChildren().add(scoreVisualizer);
    }

    public void updateScore() {
        score.setX(((WashingMachine) scoreVisualizer.getChildren().get(0)).getX() + ((WashingMachine) scoreVisualizer.getChildren().get(0)).getWidth()/2 - score.getLayoutBounds().getWidth()/2);
        score.setText(String.valueOf(game.getScore()));
    }

    public Pane getPane() {
        return pane;
    }

    private void initializeGhosts() {
        int index = pane.getChildren().indexOf(pacMan);
        Ghost greenGhost = new Ghost(game, 152+Ghost.WIDTH, 0+Ghost.HEIGHT, new Image(getClass().getResource("/Images/Ghosts/GreenGhost.png").toExternalForm()), new Image(getClass().getResource("/Images/Ghosts/GreenGhostFlipped.png").toExternalForm()));
        Ghost Imposter1 = new Ghost(game, 500 - greenGhost.WIDTH/2, 5, new Image(getClass().getResource("/Images/Ghosts/Imposter.png").toExternalForm()), new Image(getClass().getResource("/Images/Ghosts/ImposterFlipped.png").toExternalForm()));
        //Ghost blueGhost = new Ghost(game, 848-2*Ghost.WIDTH, 0+Ghost.HEIGHT, new Image(getClass().getResource("/Images/Ghosts/BlueGhost.png").toExternalForm()), new Image(getClass().getResource("/Images/Ghosts/BlueGhostFlipped.png").toExternalForm()));
        Ghost Imposter2 = new Ghost(game, 848-2*Ghost.WIDTH, 0+Ghost.HEIGHT, new Image(getClass().getResource("/Images/Ghosts/BlueGhost.png").toExternalForm()), new Image(getClass().getResource("/Images/Ghosts/BlueGhostFlipped.png").toExternalForm()));
        Ghost redGhost = new Ghost(game, 848-2*Ghost.WIDTH, 1000-2*Ghost.HEIGHT, new Image(getClass().getResource("/Images/Ghosts/RedGhost.png").toExternalForm()), new Image(getClass().getResource("/Images/Ghosts/RedGhostFlipped.png").toExternalForm()));

        ghosts.getChildren().add(Imposter1);
        ghosts.getChildren().add(redGhost);
        //ghosts.getChildren().add(blueGhost);
        ghosts.getChildren().add(greenGhost);
        ghosts.getChildren().add(Imposter2);

        GeniusGhostAnimation imposter1Animation = new GeniusGhostAnimation(this, Imposter1);
        SemiGeniusGhostAnimation redAnimation = new SemiGeniusGhostAnimation(this, redGhost, 500);
//        GhostAnimation blueAnimation = new GhostAnimation(this, blueGhost);
        GhostAnimation greenAnimation = new GhostAnimation(this, greenGhost);
        SemiGeniusGhostAnimation imposter2Animation = new SemiGeniusGhostAnimation(this, Imposter2, 600);

        ghostAnimations.add(imposter1Animation);
        ghostAnimations.add(redAnimation);
//        ghostAnimations.add(blueAnimation);
        ghostAnimations.add(greenAnimation);
        ghostAnimations.add(imposter2Animation);
        allAnimations.add(imposter1Animation);

        allAnimations.add(redAnimation);
        allAnimations.add(greenAnimation);
        allAnimations.add(imposter2Animation);

        imposter1Animation.play();
        redAnimation.play();
//        blueAnimation.play();
        greenAnimation.play();
        imposter2Animation.play();

        pane.getChildren().add(index, ghosts);
    }

    public void pauseGhosts() {
        for (Transition i : ghostAnimations) {
            i.pause();
        }
    }

    public void resumeGhosts() {
        for (Transition i : ghostAnimations) {
            i.play();
        }
    }

    public void stopAllAnimations() {
        for (Transition i : allAnimations) {
            i.stop();
        }
    }

    public void removeSpeedySocks() {
        speedySocks.getChildren().clear();
    }
}
