package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class EditRouteController implements Initializable, Listener {
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
    private TextField transportTypeField;

    private RouteEntry entry;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);
        entry = Mediator.getInstance().getCurrentRouteEntry();
        editTourTitle.setText("Edit Entry: " + entry.getName());
    }

    public void updateRouteList(RouteEntry entry){

    }

    @Override
    public void getCurrentRoute(RouteEntry currentRoute){
        entry = currentRoute;
    }

    public boolean checkUniqueEntry(String givenEntryName){
        return false;
    }

    public void updateTourLogList(LogEntry entry){
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // edit Route upon button
    public void saveChanges(){
        /* check if input is not null - if so replace it with new input
        if(!nameField.getText().isEmpty()){
            entry.setName(nameField.getText());
        }
        */
        if(!descriptionField.getText().isEmpty()){
            entry.setDescription(descriptionField.getText());
        }
        if(!startField.getText().isEmpty()){
            entry.setStart(startField.getText());
        }
        if(!destinationField.getText().isEmpty()){
            entry.setDestination(destinationField.getText());
        }
        if(!transportTypeField.getText().isEmpty()){
            entry.setTransportType(transportTypeField.getText());
        }

        Mediator.getInstance().publishRouteUpdate(entry);

        System.out.println("Saved new input");

    }

    public void closeWindow(){
        // Get the parent container of the VBox
        Parent parent = vbox.getParent();

        // Check if the parent container is a Pane
        if (parent instanceof Pane) {
            // Remove the child (VBox) from the parent container's list of children
            ((Pane) parent).getChildren().remove(vbox);
        } else {
            System.out.println("Parent is not a Pane.");
        }
    }
}
