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

public class CreateRouteController implements Initializable, Listener {

    @FXML
    private Button createEntryButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button uploadFileButton;

    @FXML
    private TextField nameField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField startField;
    @FXML
    private TextField destinationField;
    @FXML
    private TextField transportTypeField;
    @FXML
    private TextField tourDistanceField;
    @FXML
    private TextField estimatedTimeField;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);
    }

    public void updateRouteList(RouteEntry entry){

    }

    public void loadRouteInformation(RouteEntry entry){

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // create new Route upon button
    public void createNewRouteEntry(){
        System.out.println("CreateRouteController reached");
        
        RouteEntry newEntry = null;

        // create new RouteEntry Object from input
        if(nameField.getText() != null 
        && descriptionField.getText() != null
        && startField.getText() != null
        && destinationField.getText() != null
        && transportTypeField.getText() != null){
            newEntry = new RouteEntry(
                    nameField.getText(),
                    descriptionField.getText(),
                    startField.getText(),
                    destinationField.getText(),
                    transportTypeField.getText());
        }
        
        // relay information to mainview controller over mediator
        if(newEntry != null){
            Mediator.getInstance().publishRouteUpdate(newEntry);
        }
        else{
            System.out.println("Couldnt create new Route Object");
        }
    }

}
