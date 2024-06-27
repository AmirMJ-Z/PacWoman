module PacMan {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;

    exports view;
    opens view to javafx.fxml;
}