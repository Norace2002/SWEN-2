package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.backend.ReportService;
import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;

public class MainViewController implements Initializable, Listener {

    private final MainViewModel viewModel = new MainViewModel();

    FileChooser fileChooser = new FileChooser();

    ReportService reportService = new ReportService();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // references used to setup data-binding
    @FXML
    private Pane hostPane;
    @FXML
    private ListView<String> routeEntries;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Singleton implementation
    private static MainViewController instance;
    public MainViewController(){};

    public static MainViewController getInstance(){
        if(instance == null){
            instance = new MainViewController();
        }
        return instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Initialize & Listener methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setListItemClickEvent();

        Mediator.getInstance().registerListener(this);

        // bind listview to observablelist
        routeEntries.setItems(viewModel.getRouteEntries());

    }

    public void updateRouteList(RouteEntry entry){
        System.out.println("MainViewController updates RouteList");
        viewModel.updateRouteEntries(entry);
    }

    public boolean checkUniqueEntry(String givenEntryName){
        return viewModel.getRouteEntryByName(givenEntryName) == null;
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
        try {
            RouteEntry route = Mediator.getInstance().getCurrentRouteEntry();
            if (route == null) {
                System.out.println("No route selected");
            }

            String directory = "empty";

            fileChooser.setTitle("Choose a Directory to Save File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

            // show directory selection dialog
            File selectedDirectory = fileChooser.showSaveDialog(null);

            // check if a directory was selected
            if (selectedDirectory != null) {
                directory = selectedDirectory.getAbsolutePath();
                System.out.println("Selected Directory: " + directory);
            } else {
                System.out.println("No directory selected.");
            }

            // turn route into json string
            String json = viewModel.modelToJson(route);

            // Store JSON as file
            try(FileOutputStream fos = new FileOutputStream(directory)){
                fos.write(json.getBytes());
                System.out.println("Route JSON saved to " + directory);
            }

        } catch (Exception e) {
            System.out.println("Couldn't export data");
            e.printStackTrace();
        }
    }

    public void importTourDataJSON() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose a JSON File to Import");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

            // Show open file dialog
            File selectedFile = fileChooser.showOpenDialog(null);

            // Check if a file was selected
            if (selectedFile != null) {
                String filePath = selectedFile.getAbsolutePath();
                System.out.println("Selected File: " + filePath);

                // Read JSON data from file
                String json;
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    byte[] data = new byte[(int) selectedFile.length()];
                    fis.read(data);
                    json = new String(data, StandardCharsets.UTF_8);
                }

                // Convert JSON string to RouteEntry object
                RouteEntry route = viewModel.jsonToModel(json);

                // Add route accordingly to db and ViewModel
                updateRouteList(route);
                Mediator.getInstance().setCurrentRoute(route);
                System.out.println("Route imported from " + filePath);
            } else {
                System.out.println("No file selected.");
            }
        } catch (Exception e) {
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
            byte[] pdfData = reportService.getRouteReport(Mediator.getInstance().getCurrentRouteEntry());

            try(FileOutputStream fos = new FileOutputStream(directory)){
                fos.write(pdfData);
                System.out.println("PDF report saved to " + directory);
            }

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
            byte[] pdfData = reportService.getSummaryReport();

            try(FileOutputStream fos = new FileOutputStream(directory)){
                fos.write(pdfData);
                System.out.println("PDF report saved to " + directory);
            }

            System.out.println("Generating summary report on location: " + directory);
        }catch(Exception e){
            System.out.println("Couldn't generate summary report");
            e.printStackTrace();
        }
    }

}
