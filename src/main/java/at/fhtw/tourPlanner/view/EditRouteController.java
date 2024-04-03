package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class EditRouteController implements Initializable, Listener {
    @FXML
    private Button createEntryButton;
    @FXML
    private Button exitButton;

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

        System.out.println("Saved new input");

    }
}
