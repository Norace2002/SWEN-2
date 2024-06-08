package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.Main;
import at.fhtw.tourPlanner.model.LogEntry;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TextInputControlMatchers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;


class MainViewControllerTest extends ApplicationTest{


    @Override
    public void start(Stage stage) throws Exception {
        new Main().start(stage);
    }

    @Test
    void loadCreateRouteWindow() {
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Click the button to load the Create Route window
        robot.clickOn("#loadCreateRouteButton");

        // Assert
        // Verify the new FXML was loaded into the hostPane
        Pane hostPane = lookup("#hostPane").query();
        FxAssert.verifyThat("#nameField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#descriptionField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#startField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#destinationField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#transportTypeField", TextInputControlMatchers.hasText(""));
    }


    @Test
    void createEntry() {
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Click on a test Route and click on the edit button to load the Edit Route window
        robot.clickOn("#loadCreateRouteButton");

        // create a test entry
        TextField nameField = robot.lookup("#nameField").query();
        TextField descriptionField = robot.lookup("#descriptionField").query();
        TextField startField = robot.lookup("#startField").query();
        TextField destinationField = robot.lookup("#destinationField").query();
        TextField transportTypeField = robot.lookup("#transportTypeField").query();

        nameField.setText("Test");
        descriptionField.setText("Test");
        startField.setText("Austria, Vienna, Julius-Ficker-Strasse 7");
        destinationField.setText("Austria, Vienna, Dopschstrasse 12");
        transportTypeField.setText("driving-car");

        robot.clickOn("#createEntryButton");

        // Assert
        // Check if entry was made and is on the List
        ListView<String> routeList = robot.lookup("#routeEntries").queryAs(ListView.class);
        assertTrue(routeList.getItems().contains("Test"));
    }

    @Test
    void loadEditRouteWindow() {
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Click on a test Route and click on the edit button to load the Edit Route window
        robot.clickOn("#routeEntries").clickOn("Test");
        robot.clickOn("#loadEditRouteButton");



        // Assert
        // Verify that the field are not empty, because they are prefilled with their information
        TextField descriptionField = robot.lookup("#descriptionField").query();
        TextField startField = robot.lookup("#startField").query();
        TextField destinationField = robot.lookup("#destinationField").query();
        TextField transportTypeField = robot.lookup("#transportTypeField").query();

        assertThat(descriptionField.getPromptText(), not(isEmptyString()));
        assertThat(startField.getPromptText(), not(isEmptyString()));
        assertThat(destinationField.getPromptText(), not(isEmptyString()));
        assertThat(transportTypeField.getPromptText(), not(isEmptyString()));
    }

    @Test
    void editRoute(){
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Click on a test Route and click on the edit button to load the Edit Route window
        robot.clickOn("#routeEntries").clickOn("Test");
        robot.clickOn("#loadEditRouteButton");

        // Assert
        // Change description Field
        TextField descriptionField = robot.lookup("#descriptionField").query();

        descriptionField.setText("verändert");

        FxAssert.verifyThat("#descriptionField", TextInputControlMatchers.hasText("verändert"));
    }

    @Test
    void loadingLogWindow(){
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Check out if Route window is properly loaded
        robot.clickOn("#routeEntries").clickOn("Test");
        robot.clickOn("#routeButton");


        // Assert
        // Check whether the Pane has a child aka "the Map" or not
        Pane imgPane = robot.lookup("#imgPane").queryAs(Pane.class);
        assertFalse(imgPane.getChildren().isEmpty());
    }

    @Test
    void loadingCreateTourLogWindow(){
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Check out "Test" Route and create new Log Entry
        robot.clickOn("#routeEntries").clickOn("Test");
        robot.clickOn("#routeButton");
        robot.clickOn("#loadCreateTourLogWindow");

        DatePicker dateTimeField = robot.lookup("#dateTimeField").query();


        //Assert
        //Check if Create Tour Log window is open an every field is empty as it should be
        FxAssert.verifyThat("#difficultyField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#totalDistanceField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#totalTimeField", TextInputControlMatchers.hasText(""));
        assertNull(dateTimeField.getValue());
        FxAssert.verifyThat("#commentField", TextInputControlMatchers.hasText(""));
        FxAssert.verifyThat("#ratingField", TextInputControlMatchers.hasText(""));
    }

    @Test
    void createTourLog(){
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Check out "Test" Route and create new Log Entry
        robot.clickOn("#routeEntries").clickOn("Test");
        robot.clickOn("#routeButton");
        robot.clickOn("#loadCreateTourLogWindow");

        // create a test log
        TextField difficultyField = robot.lookup("#difficultyField").query();
        TextField totalDistanceField = robot.lookup("#totalDistanceField").query();
        TextField totalTimeField = robot.lookup("#totalTimeField").query();
        DatePicker dateTimeField = robot.lookup("#dateTimeField").query();
        TextField commentField = robot.lookup("#commentField").query();
        TextField ratingField = robot.lookup("#ratingField").query();

        difficultyField.setText("1");
        totalDistanceField.setText("1");
        totalTimeField.setText("1");
        commentField.setText("Test");
        ratingField.setText("1");
        // Set Date Picker
        // Create a LocalDate object with the desired date
        dateTimeField.setValue(LocalDate.now());

        robot.clickOn("#createTourLogButton");

        //Assert
        // Check if entry was made and is on the List
        TableView<LogEntry> logTable = robot.lookup("#logTable").queryAs(TableView.class);
        ObservableList<LogEntry> logEntries = logTable.getItems();
        boolean entryExists = false;
        for (LogEntry entry : logEntries) {
            if (entry.getComment().equals("Test") && entry.getRating() == 1) {
                entryExists = true;
                break;
            }
        }
        assertTrue(entryExists);
    }

    @Test
    void loadingEditTourLogWindow(){
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Click on a test Route and click on the edit button to load the Edit Route window
        robot.clickOn("#routeEntries").clickOn("Test");
        robot.clickOn("#routeButton");

        //Click on first log that we made before
        Node firstItem = robot.lookup(".table-row-cell").nth(0).query();
        robot.clickOn(firstItem);

        robot.clickOn("#loadEditTourLogWindow");



        // Assert
        // Verify that the field are not empty, because they are prefilled with their information
        TextField difficultyField = robot.lookup("#difficultyField").query();
        TextField totalDistanceField = robot.lookup("#totalDistanceField").query();
        TextField totalTimeField = robot.lookup("#totalTimeField").query();
        DatePicker dateTimeField = robot.lookup("#dateTimeField").query();
        TextField commentField = robot.lookup("#commentField").query();
        TextField ratingField = robot.lookup("#ratingField").query();


        //Check if Prompt text is filled with log information
        assertThat(difficultyField.getPromptText(), not(isEmptyString()));
        assertThat(totalDistanceField.getPromptText(), not(isEmptyString()));
        assertThat(totalTimeField.getPromptText(), not(isEmptyString()));
        assertThat(dateTimeField.getPromptText(), not(isEmptyString()));
        assertThat(commentField.getPromptText(), not(isEmptyString()));
        assertThat(ratingField.getPromptText(), not(isEmptyString()));
    }

    @Test
    void deleteTourLog(){
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Click on a test Route and click on the edit button to load the Edit Route window
        robot.clickOn("#routeEntries").clickOn("Test");
        robot.clickOn("#routeButton");

        //Click on first log that we made before
        Node firstItem = robot.lookup(".table-row-cell").nth(0).query();
        robot.clickOn(firstItem);

        robot.clickOn("#deleteEntry");

        //Assert
        // Check if entry was deleted or is still on the list
        TableView<LogEntry> logTable = robot.lookup("#logTable").queryAs(TableView.class);
        ObservableList<LogEntry> logEntries = logTable.getItems();
        boolean entryExists = false;
        for (LogEntry entry : logEntries) {
            if (entry.getComment().equals("Test") && entry.getRating() == 1) {
                entryExists = true;
                break;
            }
        }
        assertFalse(entryExists);
    }

    @Test
    void deleteEntry() {
        // Arrange
        FxRobot robot = new FxRobot();

        // Act
        // Click on a test Route and click on the edit button to load the Edit Route window
        robot.clickOn("#routeEntries").clickOn("Test");
        robot.clickOn("#deletingRoute");


        // Assert
        ListView<String> routeList = robot.lookup("#routeEntries").queryAs(ListView.class);
        assertFalse(routeList.getItems().contains("Test"));
    }

}