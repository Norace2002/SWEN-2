package at.fhtw.tourPlanner.view;
import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.model.LogEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateTourLogController implements Initializable, Listener {

    private Stage stage;

    @FXML
    private Button createTourLogButton;
    @FXML
    private Button exitButton;

    @FXML
    private TextField difficultyField;
    @FXML
    private TextField totalDistanceField;
    @FXML
    private TextField totalTimeField;
    @FXML
    private DatePicker dateTimeField;
    @FXML
    private TextField commentField;
    @FXML
    private TextField ratingField;


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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void createNewTourLog(){
        LogEntry newEntry = null;

        // check if input is not null
        if(!difficultyField.getText().isEmpty() && !totalDistanceField.getText().isEmpty() && !totalTimeField.getText().isEmpty()
                && dateTimeField.getValue() != null && !commentField.getText().isEmpty() && !ratingField.getText().isEmpty()){
            // create new RouteEntry Object from input
            newEntry = new LogEntry(1, dateTimeField.getValue().toString(), "14:32", commentField.getText(), Integer.parseInt(difficultyField.getText()),
                    Double.parseDouble(totalDistanceField.getText()), Double.parseDouble(totalTimeField.getText()), Integer.parseInt(ratingField.getText()));
        }

        // relay information to RouteWindowController controller over mediator
        if(newEntry != null){
            Mediator.getInstance().publishLogEntry(newEntry);
        }
        else{
            System.out.println("Couldnt create new Tour Log Object");
        }




    }
}
