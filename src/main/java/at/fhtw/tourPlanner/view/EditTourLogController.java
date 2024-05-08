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
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLogController implements Initializable, Listener {

    private Stage stage;
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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);
        entry = Mediator.getInstance().getCurrentLogEntry();
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
                Date date = Date.valueOf(localDate);
                entry.setDate(date);
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
}
