package at.fhtw.tourPlanner.view;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateTourLogController implements Initializable {

    private Stage stage;

    @FXML
    public VBox vbox; //current Window

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
    //Logger Set up
    private static final Logger logger = LogManager.getLogger(CreateTourLogController.class);
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setStage(Stage stage) {
        this.stage = stage;
    }



    public void createNewTourLog(){
        LogEntry newEntry = null;
        String routeEntryName = Mediator.getInstance().getCurrentRouteEntry().getName();

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
                LocalDate date = dateTimeField.getValue();
                String dateString = date.toString();

                System.out.println("Hiiiiiiiiilllllfe: " + dateTimeField.getValue());

                // ------ Get the current time ------
                LocalTime now = LocalTime.now();

                // Define the format for the time
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                // Convert the time to a string in the desired format
                String formattedTime = now.format(formatter);
                // ----------------------------------

                // create new RouteEntry Object from input
                newEntry = new LogEntry(dateString, formattedTime, commentField.getText(), difficulty,
                        totalDistance, totalTime, rating, routeEntryName);

                //---------------- handle response---------------------------------------
                //handle server response (id)
                int id = LogMediator.getInstance().addEntry(newEntry);
                newEntry.setId(id);
                logger.info("successfully created an instance of a Tour Log entry - Sending to Log Mediator");
                //----------------------------------------------
            } catch (NumberFormatException e) {
                WrongLogInput(e);
                logger.error("Invalid input for one or more fields while trying to create a new tour log for route: " + routeEntryName);
            }
        } else {
            logger.error("Failed to create new Route Object - One or more fields were empty" );
        }
    }

    // Closes the creating window (vbox)
    public void closeWindow(){
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
        logger.debug("'Create Tour Log' Window closed successfully");
    }


    // Gives the User a visual feedback when giving invalid input
    private void WrongLogInput(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Your input doesn't seem to fit into the given field, please try again!");
        alert.setContentText(e.getMessage());

        alert.showAndWait();
    }

}
