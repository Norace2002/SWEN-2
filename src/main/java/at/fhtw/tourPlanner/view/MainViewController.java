package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final MainViewModel viewModel = new MainViewModel();

    // references used to setup data-binding
    @FXML
    private Pane hostPane;
    @FXML
    private VBox routeEntries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabelClickHandler();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void loadCreateRouteWindow(){
        System.out.println("load route creation window");

        try{
            hostPane.getChildren().clear();

            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/createTour.fxml"));
            hostPane.getChildren().add(newLoadedPane);
        } catch(Exception e){
            System.out.println("Problem loading FXML into Pane");
            e.printStackTrace();
        }
    }
    public void loadRouteMenu(Label label){
        try{

            hostPane.getChildren().clear();
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/routeMenu.fxml"));
            hostPane.getChildren().add(newLoadedPane);
            System.out.println(label.getText());
        } catch(Exception e){
            System.out.println("Problem loading FXML into Pane");
            e.printStackTrace();
        }
    }

    private void setLabelClickHandler() {
        for (javafx.scene.Node node : routeEntries.getChildren()) {
            if (node instanceof Label label) {
                label.setOnMouseClicked(event -> loadRouteMenu(label));
            }
        }
    }
}
