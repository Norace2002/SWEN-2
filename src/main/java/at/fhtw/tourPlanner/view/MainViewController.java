package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import javax.print.attribute.standard.Media;
import javax.sound.midi.Soundbank;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;

public class MainViewController implements Initializable, Listener {

    private final MainViewModel viewModel = new MainViewModel();

    FileChooser fileChooser = new FileChooser();

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
        viewModel.updateRouteEntries(entry);
    }

    public void getCurrentRoute(RouteEntry currentRoute){

    }

    public boolean checkUniqueEntry(String givenEntryName){
        return viewModel.getRouteEntryByName(givenEntryName) == null;
    }

    public void updateTourLogList(LogEntry entry){
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Method to make list items clickable

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
        if(Mediator.getInstance().getCurrentRouteEntry() == null) {
            System.out.println("current Route not set");
        }
        else{
            try{
                hostPane.getChildren().clear();
                Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/editTour.fxml"));
                hostPane.getChildren().add(newLoadedPane);
            } catch(Exception e){
                System.out.println("Problem loading EditTour FXML into Pane");
                e.printStackTrace();
            }
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

    public void deleteRoute() {

        //get selected Entry and call methode from viewModel
        String selectedItem = routeEntries.getSelectionModel().getSelectedItem();
        viewModel.deleteRouteEntry(selectedItem);

        //clear pain of deleted entry
        hostPane.getChildren().clear();

        //deselect current Route
        Mediator.getInstance().deselectCurrentRoute();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // imports/exports

    public void exportTourDataJSON(){
        try{
            RouteEntry route = Mediator.getInstance().getCurrentRouteEntry();
            String directory = "empty";

            fileChooser.setTitle("Choose a Directory to Save File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON Files", "*.json")
            );

            // Show directory selection dialog
            File selectedDirectory = fileChooser.showSaveDialog(null);

            // Check if a directory was selected
            if (selectedDirectory != null) {
                directory = selectedDirectory.getAbsolutePath();
                System.out.println("Selected Directory: " + directory);
            } else {
                System.out.println("No directory selected.");
            }

            // map current route entry + log objects to JSON

            // ...

            // store JSON as file in directory

            // ...

            System.out.println("Exporting Tourdata for: " + route.getName() + " on location: " + directory);
        }catch(Exception e){
            System.out.println("Couldn't export data");
            e.printStackTrace();
        }
    }

    public void importTourDataJSON(){
        String filepath = "empty";

        try{
            fileChooser.setTitle("Choose a Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON Files", "*.json")
            );

            // Show open file dialog
            File selectedFile = fileChooser.showOpenDialog(null);

            // Check if a file was selected
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                System.out.println("Selected File: " + filePath);
            } else {
                System.out.println("No file selected.");
            }

            // map json data to route entry object

            // ...

        }catch(Exception e){
            System.out.println("Couldn't import data");
            e.printStackTrace();
        }
    }

    public void generateRouteReport(){
        try{
            RouteEntry route = Mediator.getInstance().getCurrentRouteEntry();
            String directory = "empty";

            fileChooser.setTitle("Choose a Directory to Save File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
            );

            // Show directory selection dialog
            File selectedDirectory = fileChooser.showSaveDialog(null);

            // Check if a directory was selected
            if (selectedDirectory != null) {
                directory = selectedDirectory.getAbsolutePath();
                System.out.println("Selected Directory: " + directory);
            } else {
                System.out.println("No directory selected.");
            }

            // generate Report based on current Route

            // ...

            System.out.println("Generating Report for: " + route.getName() + " on location: " + directory);
        }catch(Exception e){
            System.out.println("Couldn't generate route report");
            e.printStackTrace();
        }
    }

    public void generateSummaryReport(){
        try{
            String directory = "empty";

            fileChooser.setTitle("Choose a Directory to Save File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
            );

            // Show directory selection dialog
            File selectedDirectory = fileChooser.showSaveDialog(null);

            // Check if a directory was selected
            if (selectedDirectory != null) {
                directory = selectedDirectory.getAbsolutePath();
                System.out.println("Selected Directory: " + directory);
            } else {
                System.out.println("No directory selected.");
            }

            // generate summary report

            // ...

            System.out.println("Generating summary report on location: " + directory);
        }catch(Exception e){
            System.out.println("Couldn't generate summary report");
            e.printStackTrace();
        }
    }

}
