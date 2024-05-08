package at.fhtw.tourPlanner.view;
import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.LogMediator;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.backend.LogService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.net.URL;
import java.util.ResourceBundle;

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
        String routeEntryName = Mediator.getInstance().getCurrentRouteEntry().getName();

        // Origin Format
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        // check if input is not null
        if (!difficultyField.getText().isEmpty() &&
                !totalDistanceField.getText().isEmpty() &&
                !totalTimeField.getText().isEmpty() &&
                dateTimeField.getValue() != null &&
                !commentField.getText().isEmpty() &&
                !ratingField.getText().isEmpty()){

            try {
                // Check for intended type
                int difficulty = Integer.parseInt(difficultyField.getText());
                double totalDistance = Double.parseDouble(totalDistanceField.getText());
                double totalTime = Double.parseDouble(totalTimeField.getText());
                int rating = Integer.parseInt(ratingField.getText());

                // parse dateFormat input from dd-MM-yyyy to yyy-MM-dd format
                LocalDate originalDate = LocalDate.parse(dateTimeField.getValue().toString(), originalFormatter);
                // Convert LocalDate to Date
                Date newDate = Date.valueOf(originalDate);

                // create new RouteEntry Object from input
                newEntry = new LogEntry(newDate, commentField.getText(), difficulty,
                        totalDistance, totalTime, rating, routeEntryName);



                //---------------- handle response---------------------------------------


                /*handle server response (id)
                eg.: int id = LogMediator.getInstance().addEntry(newEntry);
                newEntry.setId(id);
                */

                LogMediator.getInstance().addEntry(newEntry);

                //----------------------------------------------

                //set newEntry via Mediator
                Mediator.getInstance().publishLogEntry(newEntry);


            } catch (NumberFormatException e) {
                // Wenn eines der Felder keine gültige Zahl ist, wird eine NumberFormatException ausgelöst
                System.err.println("Invalid input for one or more fields");
            }


        } else {
            System.out.println("One or more fields are empty");
        }




    }
}
