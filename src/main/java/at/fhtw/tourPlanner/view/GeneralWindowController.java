package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralWindowController implements Initializable, Listener {
    @FXML
    public Label tourNameLabel;
    @FXML
    public Text tourDescriptionText;
    @FXML
    public Label tourStartLabel;
    @FXML
    public Label tourDestinationLabel;
    @FXML
    public Label tourDistanceLabel;
    @FXML
    public Label tourDurationLabel;
    @FXML
    public Label tourTransportTypeLabel;


    private RouteEntry currentEntry;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);

        // get currently active route
        currentEntry = Mediator.getInstance().getCurrentRouteEntry();

        // set URL for discerning windows
        System.out.println("calling loadInformation()");
        this.loadInformation();
    }

    @Override
    public void updateRouteList(RouteEntry entry) {

    }

    @Override
    public void getCurrentRoute(RouteEntry currentRoute){
        currentEntry = currentRoute;
    }

    private void loadInformation(){
        tourNameLabel.setText(currentEntry.getName());
        tourStartLabel.setText(currentEntry.getStart());
        tourDestinationLabel.setText(currentEntry.getDestination());
        tourTransportTypeLabel.setText(currentEntry.getTransportType());
        tourDescriptionText.setText(currentEntry.getDescription());
    }

}
