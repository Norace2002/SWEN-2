package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;


public class EditRouteController implements Initializable{
    public Text editTourTitle;

    @FXML
    public VBox vbox; //current Window

    /*
    @FXML
    private TextField nameField;
    */
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField startField;
    @FXML
    private TextField destinationField;
    @FXML
    private ComboBox transportTypeField;

    private RouteEntry entry;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Logger Set up
    private static final Logger logger = LogManager.getLogger(EditRouteController.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // initialization

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entry = Mediator.getInstance().getCurrentRouteEntry();
        editTourTitle.setText("Edit Entry: " + entry.getName());

        descriptionField.promptTextProperty().set(entry.getDescription());
        startField.promptTextProperty().set(entry.getStart());
        destinationField.promptTextProperty().set(entry.getDestination());
        transportTypeField.promptTextProperty().set(entry.getTransportType());
        transportTypeField.setValue(entry.getTransportType());
        transportTypeField.getItems().addAll("driving-car", "foot-walking", "cycling-regular");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // edit Route upon button
    public void saveChanges(){
        if(!descriptionField.getText().isEmpty()){
            entry.setDescription(descriptionField.getText());
        }
        if(!startField.getText().isEmpty()){
            entry.setStart(startField.getText());
        }
        if(!destinationField.getText().isEmpty()){
            entry.setDestination(destinationField.getText());
        }
        if(!transportTypeField.getItems().isEmpty()){
            entry.setTransportType(String.valueOf(transportTypeField.getValue()));
        }



        Mediator.getInstance().publishRouteUpdate(entry);

        logger.info("No problems occurred while altering route entry - Sending build entry to Mediator");

    }

    public void closeWindow(){
        // Get the parent container of the VBox
        Parent parent = vbox.getParent();

        // Check if the parent container is a Pane
        if (parent instanceof Pane) {
            // Remove the child (VBox) from the parent container's list of children
            ((Pane) parent).getChildren().remove(vbox);
            logger.debug("'Edit Route' Window closed successfully");
        } else {
            logger.error("Failed to close window, because given Window is not a child of a parent with type pane");
        }
    }
}
