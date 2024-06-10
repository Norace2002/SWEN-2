package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class RouteMenuController implements Initializable{

    @FXML
    Pane contentWindowPane;

    RouteEntry currentEntry;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Logger Set up
    private static final Logger logger = LogManager.getLogger(RouteMenuController.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // load general route information window
        loadGeneralWindow();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void loadGeneralWindow(){
        logger.debug("load general window");

        try{
            contentWindowPane.getChildren().clear();

            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/generalWindow.fxml"));
            contentWindowPane.getChildren().add(newLoadedPane);
        } catch(Exception e){
            logger.error("Problem loading GeneralWindow FXML into Pane - something went wrong with loading newPane into hostPane");
            e.printStackTrace();
        }
    }

    public void loadRouteWindow(){
        logger.debug("load route window");

        try{
            contentWindowPane.getChildren().clear();

            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("/at/fhtw/tourPlanner/routeWindow.fxml"));
            contentWindowPane.getChildren().add(newLoadedPane);
        } catch(Exception e){
            logger.error("Problem loading RouteWindow FXML into Pane - something went wrong with loading newPane into hostPane");
            e.printStackTrace();
        }
    }

}
