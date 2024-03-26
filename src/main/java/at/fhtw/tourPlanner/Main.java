package at.fhtw.tourPlanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        showStage(primaryStage);
    }
    public static Parent showStage(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Main.class.getResource("mainWindowV2.fxml"));
        primaryStage.setTitle("TourPlanner");
        primaryStage.setScene(new Scene(root, 900, 550));
        primaryStage.setMinWidth(900);
        primaryStage.setMaxWidth(1100);
        primaryStage.setMinHeight(550);
        primaryStage.setResizable(true);
        primaryStage.show();

        return root;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
