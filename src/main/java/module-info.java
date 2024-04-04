module tourPlanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires lombok;

    opens at.fhtw.tourPlanner to javafx.graphics, javafx.fxml;
    exports at.fhtw.tourPlanner;
    exports at.fhtw.tourPlanner.model;
    exports at.fhtw.tourPlanner.viewmodel;
    opens at.fhtw.tourPlanner.viewmodel to javafx.fxml, javafx.graphics;
    exports at.fhtw.tourPlanner.view;
    opens at.fhtw.tourPlanner.view to javafx.fxml, javafx.graphics;
    exports at.fhtw.tourPlanner.listener;
    opens at.fhtw.tourPlanner.listener to javafx.fxml, javafx.graphics;
}
