package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import javax.print.attribute.standard.Media;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, Listener {

    private final MainViewModel viewModel = new MainViewModel();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // references used to setup data-binding
    @FXML
    private Pane hostPane;
    @FXML
    private ListView<String> routeEntries;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListItemClickEvent();

        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);

        // bind listview to observablelist
        routeEntries.setItems(viewModel.getRouteEntries());
    }

    public void updateRouteList(RouteEntry entry){
        System.out.println("MainViewController updates RouteList");
        viewModel.addNewRouteEntry(entry);
    }

    public void getCurrentRoute(RouteEntry currentRoute){

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // UI interaction methods

    public void loadCreateRouteWindow(){
        System.out.println("load route creation window");

        try{
            hostPane.getChildren().clear();
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/createTour.fxml"));
            hostPane.getChildren().add(newLoadedPane);
        } catch(Exception e){
            System.out.println("Problem loading CreateTour FXML into Pane");
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
            System.out.println("Problem loading EditTour FXML into Pane");
            e.printStackTrace();
        }
    }

    public void loadRouteMenu(String routeName){
        try{
            hostPane.getChildren().clear();
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/routeMenu.fxml"));
            hostPane.getChildren().add(newLoadedPane);
        } catch(Exception e){
            System.out.println("Problem loading RouteMenu FXML into Pane");
            e.printStackTrace();
        }
    }

    private void setListItemClickEvent() {
        routeEntries.setOnMouseClicked(event -> {
            String selectedItem = routeEntries.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // handle specific route information through mediator
                RouteEntry chosenEntry = viewModel.getRouteEntryByName(selectedItem);

                // call Listener function for RouteMenuController
                Mediator.getInstance().setCurrentRoute(chosenEntry);

                // load RouteMenu for window selection
                loadRouteMenu(selectedItem);
            }
        });
    }

    public  void deleteRoute() {
        //get selected Entry and call methode from viewModel
        String selectedItem = routeEntries.getSelectionModel().getSelectedItem();
        viewModel.deleteRouteEntry(selectedItem);

        //clear pain of deleted entry
        hostPane.getChildren().clear();

        System.out.println("Entry '" + selectedItem + "' deleted");

    }

}
