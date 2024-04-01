package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class RouteMenuController implements Initializable, Listener {

    @FXML
    Pane contentWindowPane;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadGeneralWindow();
    }

    @Override
    public void updateRouteList(RouteEntry entry) {

    }

    public void loadRouteInformation(RouteEntry entry){

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void loadGeneralWindow(){
        System.out.println("load general window");

        try{
            contentWindowPane.getChildren().clear();

            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/generalWindow.fxml"));
            contentWindowPane.getChildren().add(newLoadedPane);
        } catch(Exception e){
            System.out.println("Problem loading FXML into Pane");
            e.printStackTrace();
        }
    }

    public void loadRouteWindow(){
        System.out.println("load route window");

        try{
            contentWindowPane.getChildren().clear();

            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/routeWindow.fxml"));
            contentWindowPane.getChildren().add(newLoadedPane);
        } catch(Exception e){
            System.out.println("Problem loading FXML into Pane");
            e.printStackTrace();
        }
    }

    public void loadMiscWindow(){
        System.out.println("load misc window");

        try{
            contentWindowPane.getChildren().clear();

            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/miscWindow.fxml"));
            contentWindowPane.getChildren().add(newLoadedPane);
        } catch(Exception e){
            System.out.println("Problem loading FXML into Pane");
            e.printStackTrace();
        }
    }
}
