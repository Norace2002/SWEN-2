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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // create new Route upon button
    public void createNewRouteEntry(){
        RouteEntry newEntry = null;


        if(!nameField.getText().isEmpty() && Mediator.getInstance().checkUniqueIdentifier(nameField.getText())){
            // check if input is not null
            if(!nameField.getText().isEmpty() && !descriptionField.getText().isEmpty() && !startField.getText().isEmpty()
                    && !destinationField.getText().isEmpty() && !transportTypeField.getText().isEmpty()){
                // create new RouteEntry Object from input
                newEntry = new RouteEntry(nameField.getText(), descriptionField.getText(), startField.getText(),
                        destinationField.getText(), transportTypeField.getText());
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

}
