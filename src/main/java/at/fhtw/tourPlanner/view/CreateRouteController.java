package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.CreateRouteMediator;
import at.fhtw.tourPlanner.viewmodel.MainViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateRouteController extends abstractController implements Initializable {

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // create new Route upon button
    public void createNewRouteEntry(){
        System.out.println("new route added to list");

        // taking information from input fields
        // ...

        // relay information to mainview controller over mediator
        routeMediator.copyRouteName("Test");
        routeMediator.copyRouteDescription("small description");
        routeMediator.copyRouteStart("Bikini");
        routeMediator.copyRouteDestination("Bottom");
        routeMediator.copyRouteTransportationType("Bootmobil");
    }
}
