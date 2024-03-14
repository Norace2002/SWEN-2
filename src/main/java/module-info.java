module highscores {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens at.fhtw.tourPlanner to javafx.graphics, javafx.fxml;
    exports at.fhtw.tourPlanner;
    exports at.fhtw.tourPlanner.model;
}
