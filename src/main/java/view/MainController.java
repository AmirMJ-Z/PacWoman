package view;

import javafx.scene.input.MouseEvent;
import model.App;

public class MainController {
    public void login(MouseEvent mouseEvent) throws Exception {
        Login login = new Login();
        login.start(App.app.getStage());
    }

    public void signup(MouseEvent mouseEvent) throws Exception {
        Signup signup = new Signup();
        signup.start(App.app.getStage());
    }
}
