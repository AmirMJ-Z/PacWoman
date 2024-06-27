package controller;

import model.Game;
import view.GameLauncher;
import view.animations.PacManAnimation;

public class GameController {
    private GameLauncher gameLauncher;
    private Game game;
    public GameController(GameLauncher gameLauncher) {
        this.gameLauncher = gameLauncher;
        this.game = gameLauncher.getGame();
        PacManAnimation pacManAnimation = new PacManAnimation(gameLauncher);
        pacManAnimation.play();
    }


}
