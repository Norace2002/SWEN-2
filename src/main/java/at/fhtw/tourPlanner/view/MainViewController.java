package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends abstractController implements Initializable {

    private final MainViewModel viewModel = new MainViewModel();

    // references used to setup data-binding
    @FXML
    private Pane hostPane;
    @FXML
    private ListView<Label> routeEntries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabelClickHandler();

        // bind listview to observablelist
        // ????
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

    public void loadEditRouteWindow(){
        System.out.println("load route editing window");

        try{
            hostPane.getChildren().clear();

            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/editTour.fxml"));
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
        // Clear any existing click handlers
        routeEntries.getItems().forEach(label -> label.setOnMouseClicked(null));
        System.out.println("Debug: existing handlers cleared");

        // Iterate over the items in the ListView
        routeEntries.getItems().forEach(label -> {
            System.out.println("Debug: label entry " + label.getText());
            label.setOnMouseClicked(event -> loadRouteMenu(label));
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void addRouteEntry(){
        // take information from mediator
        viewModel.addNewRouteEntryToList(
                routeMediator.relayRouteName(),
                routeMediator.relayRouteDescription(),
                routeMediator.relayRouteStart(),
                routeMediator.relayRouteDestination(),
                routeMediator.relayRouteTransportationType()
        );
    }
}
