package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateRouteController implements Initializable, Listener {


    @FXML
    public VBox vbox; //current Window

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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);
    }

    public void updateRouteList(RouteEntry entry){

    }

    @Override
    public void getCurrentRoute(RouteEntry currentRoute){
    }

    public boolean checkUniqueEntry(String givenEntryName){
        return false;
    }

    public void updateTourLogList(LogEntry entry){
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // create new Route upon button
    public void createNewRouteEntry(){
        RouteEntry newEntry = null;


        if(!nameField.getText().isEmpty() && Mediator.getInstance().checkUniqueRouteEntryIdentifier(nameField.getText())){
            // check if input is not null
            if(!nameField.getText().isEmpty() && !descriptionField.getText().isEmpty() && !startField.getText().isEmpty()
                    && !destinationField.getText().isEmpty() && !transportTypeField.getText().isEmpty()){
                // create new RouteEntry Object from input
                newEntry = new RouteEntry(nameField.getText(), descriptionField.getText(), startField.getText(),
                        destinationField.getText(), transportTypeField.getText(), 0, 0, 0, 0);
            }

            // relay information to mainview controller over mediator
            if(newEntry != null){
                Mediator.getInstance().publishRouteUpdate(newEntry);
            }
            else{
                System.out.println("Couldnt create new Route Object");
            }
        }
        else{
            System.out.println("Current tour name isn't available");
        }


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
