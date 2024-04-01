package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralWindowController implements Initializable, Listener {
    @FXML
    public Label tourNameLabel;
    private RouteEntry currentEntry;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);

        // get currently active route
        Mediator.getInstance().publishCurrentRoute();

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
    }

}
