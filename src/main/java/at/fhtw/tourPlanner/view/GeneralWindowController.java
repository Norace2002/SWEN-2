package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralWindowController implements Initializable{
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
    //Logger Set up
    private static final Logger logger = LogManager.getLogger(GeneralWindowController.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // initialization

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // get currently active route
        currentEntry = Mediator.getInstance().getCurrentRouteEntry();

        // set URL for discerning windows
        logger.debug("loading general information for route: " + currentEntry.getName());
        this.loadInformation();
    }


    //////////////////////////////////////////////////////////////////////

    private void loadInformation(){
        tourNameLabel.setText(currentEntry.getName());
        tourStartLabel.setText(currentEntry.getStart());
        tourDestinationLabel.setText(currentEntry.getDestination());
        tourTransportTypeLabel.setText(currentEntry.getTransportType());
        tourDescriptionText.setText(currentEntry.getDescription());
        tourDistanceLabel.setText(currentEntry.getDistance() + "m");
        tourDurationLabel.setText(currentEntry.getTime() + "s");
    }

}
