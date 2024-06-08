package at.fhtw.tourPlanner.view;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


import java.net.URL;
import java.util.ResourceBundle;

//@ExtendWith(MockitoExtension.class)
class CreateRouteControllerTest extends ApplicationTest{

    /*
    private TextField nameField;

    private TextField descriptionField;

    private TextField startField;

    private TextField destinationField;

    private TextField transportTypeField;

    @Mock
    private Mediator mediator;

    @Mock
    private Logger logger;

    @InjectMocks
    private CreateRouteController controller;

    @BeforeEach
    void setUp() throws Exception{
        // Initialize the fx components
        //ApplicationTest.launch(CreateRouteController.class);

        nameField = new TextField();
        descriptionField = new TextField();
        startField = new TextField();
        destinationField = new TextField();
        transportTypeField = new TextField();

        controller.nameField = nameField;
        controller.descriptionField = descriptionField;
        controller.startField = startField;
        controller.destinationField = destinationField;
        controller.transportTypeField = transportTypeField;
        controller.logger = logger;

        // Initialisieren Sie die Mocks
        MockitoAnnotations.openMocks(this);

        // Mock den Aufruf von Mediator.getInstance() und geben Sie den Mock zurück
        Mockito.when(Mediator.getInstance()).thenReturn(mediator);
    }

    @Override
    public void start(Stage stage) {
        // This is required by TestFX but can be left empty
    }

    @Test
    void createNewRouteEntry_successful() {
        // Arrange
        when(mediator.checkUniqueRouteEntryIdentifier("UniqueName")).thenReturn(true);
        controller.nameField.setText("Test");
        controller.descriptionField.setText("Test");
        controller.startField.setText("Test");
        controller.destinationField.setText("Test");
        controller.transportTypeField.setText("Test");

        // Act
        controller.createNewRouteEntry();

        // Assert
        verify(mediator, atLeastOnce()).publishRouteUpdate(any(RouteEntry.class));
        verify(logger, atLeastOnce()).info(contains("New route entry created and published - Route entry name: UniqueName"));
    }

    @Test
    void createNewRouteEntry_fieldEmpty() {
        // Arrange
        when(mediator.checkUniqueRouteEntryIdentifier("UniqueName")).thenReturn(true);
        controller.nameField.setText("Test");
        controller.descriptionField.setText("");
        controller.startField.setText("");
        controller.destinationField.setText("Test");
        controller.transportTypeField.setText("Test");

        // Act
        controller.createNewRouteEntry();

        // Assert
        verify(mediator, never()).publishRouteUpdate(any(RouteEntry.class));
        verify(logger, atLeastOnce()).error(contains("Failed to create new Route Object - One or more fields were empty"));
    }

    @Test
    void createNewRouteEntry_nameNotUnique() {
        // Arrange
        when(mediator.checkUniqueRouteEntryIdentifier("NotUniqueName")).thenReturn(false);

        // Act
        controller.createNewRouteEntry();

        // Assert
        verify(mediator, never()).publishRouteUpdate(any(RouteEntry.class));
        verify(logger, atLeastOnce()).error(contains("Tour name is not available or already exists"));
    }
    */

}