package at.fhtw.tourPlanner.view;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RouteMenuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void loadGeneralWindow(){
        System.out.println("general window load");
    }

    public void loadRouteWindow(){
        System.out.println("route window load");
    }

    public void loadMiscWindow(){
        System.out.println("misc window load");
    }
}
