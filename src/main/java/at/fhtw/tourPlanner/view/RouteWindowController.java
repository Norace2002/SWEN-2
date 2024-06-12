package at.fhtw.tourPlanner.view;


import at.fhtw.tourPlanner.backend.OpenrouteService;

import at.fhtw.tourPlanner.mediator.LogMediator;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

//libraries to keep popups in bounds
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;


public class RouteWindowController implements Initializable{

    OpenrouteService openrouteService = new OpenrouteService();

    private RouteEntry entry;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @FXML
    private TableView logTable;
    @FXML
    private TableColumn logDate;
    @FXML
    private TableColumn logDuration;
    @FXML
    private TableColumn logDistance;

    @FXML
    public Pane imgPane;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Logger Set up
    private static final Logger logger = LogManager.getLogger(RouteWindowController.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableItemClickEvent();

        // get currently chosen entry
        entry = Mediator.getInstance().getCurrentRouteEntry();

        // ------------- create tileMap --------------
        //addImage(entry);
        generateWebViewImage(entry);
        // -------------------------------------------


        // bind tableView to list
        logDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        logDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        logDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        logTable.setItems(LogMediator.getInstance().getLogList());
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Method to make list items clickable

    private void setTableItemClickEvent() {
        logTable.setOnMouseClicked(event -> {
            LogEntry selectedItem = (LogEntry) logTable.getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                // call Listener function for RouteMenuController
                LogMediator.getInstance().setCurrentLog(selectedItem);
            }
        });
        logger.debug("Added clickable property to logTable");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // UI interaction methods

    public void deleteEntry () throws IOException {

        if (LogMediator.getInstance().getCurrentLogEntry() != null){
            //get selected Entry and call methode from viewModel via LogMediator
            LogEntry selectedItem = (LogEntry) logTable.getSelectionModel().getSelectedItem();
            LogMediator.getInstance().deleteEntry(selectedItem.getId());

            //deselect current Route
            LogMediator.getInstance().deselectCurrentLog();

            logger.info("Log with id: '" + selectedItem.getId() + "' deleted");
        }
        else {
            logger.error("You have to select a log entry before deleting");
        }


    }

    public void loadCreateTourLogWindow(ActionEvent event) {
        logger.debug("load route Log creation window");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/fhtw/tourPlanner/createTourLog.fxml"));
            Parent root = loader.load();
            CreateTourLogController controller = loader.getController();

            Stage popupStage = new Stage();
            popupStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            popupStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            controller.setStage(popupStage);

            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEditTourLogWindow(ActionEvent event){
        if (LogMediator.getInstance().getCurrentLogEntry() != null){
            logger.debug("load route Log creation window");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/fhtw/tourPlanner/editTourLog.fxml"));
                Parent root = loader.load();
                EditTourLogController controller = loader.getController();

                Stage popupStage = new Stage();
                popupStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                popupStage.initModality(Modality.WINDOW_MODAL);

                Scene scene = new Scene(root);
                popupStage.setScene(scene);

                controller.setStage(popupStage);

                popupStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("You have to select a log entry before editing");
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // methods concerning tileMap

    //---- Required for Webview ----

    //Generates directions
    private void generateWebViewImage(RouteEntry entry){
        //Show Image in default browser
        writeGeoJsonToFile(openrouteService.getDirections(entry));

        //Reload Browser to show the updated directions.js
        try {
            // Pfad zur HTML-Datei, die die Karte anzeigt
            String htmlFilePath = new File("src/main/resources/leaflet.html").toURI().toURL().toString();
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(htmlFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("WebView from route: " + entry.getName() + " is Ready to be displayed");
    }

    //Write geoJson to directions.js
    private void writeGeoJsonToFile(String geoJson){
        try (FileWriter writer = new FileWriter("src/main/resources/directions.js")) {
            writer.write("var directions = ");
            writer.write(geoJson);
            writer.write(";");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
