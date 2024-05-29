package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.backend.LogService;
import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.LogMediator;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLogController implements Initializable{

    private Stage stage;

    @FXML
    public VBox vbox; //current Window
    private LogService logService;

    @FXML
    private Button saveChangesButton;
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

    private LogEntry entry;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entry = LogMediator.getInstance().getCurrentLogEntry();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Checks if input field is empty - if so override the old data
    public void saveTourLogChanges(){
        if(!difficultyField.getText().isEmpty()){
            try{
                entry.setDifficulty(Integer.parseInt(difficultyField.getText()));
            } catch (NumberFormatException e){
                System.err.println("Invalid inout for difficulty");
            }
        }
        if(!totalDistanceField.getText().isEmpty()){
            try{
                entry.setDistance(Integer.parseInt(totalDistanceField.getText()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid inout for distance");
            }
        }
        if(!totalTimeField.getText().isEmpty()){
            try{
                entry.setDuration(Integer.parseInt(totalTimeField.getText()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid inout for duration");
            }
        }
        if(dateTimeField.getValue() != null){
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dateTimeField.getValue().toString(), dateFormatter);
                entry.setDate(localDate.toString());
            } catch (Exception e) {
                System.err.println("Invalid input for date");
            }
        }
        if(!commentField.getText().isEmpty()){
            entry.setComment(commentField.getText());
        }
        if(!ratingField.getText().isEmpty()){
            try {
                entry.setRating(Integer.parseInt(ratingField.getText()));
            } catch (NumberFormatException e) {
                System.err.println("Invalid input for rating");
            }
        }


        System.out.println("Saved new input");
        //send to viewModel - DB
        LogMediator.getInstance().editEntry(entry);

    }

    public void closeWindow(){
        Stage stage = (Stage) vbox.getScene().getWindow();
        stage.close();
    }

}
