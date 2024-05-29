package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.backend.OpenrouteService;
import at.fhtw.tourPlanner.backend.OsmService;
import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.LogMediator;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

//libraries to keep popups in bounds
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RouteWindowController implements Initializable, Listener {
    OpenrouteService openrouteService = new OpenrouteService();
    OsmService osmService = new OsmService();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private RouteEntry entry;

    private JsonNode geoJson;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TableView logTable;
    @FXML
    private TableColumn logDate;
    @FXML
    private TableColumn logDuration;
    @FXML
    private TableColumn logDistance;

    @FXML
    public Pane imgPane;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableItemClickEvent();

        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);

        // get currently chosen entry
        entry = Mediator.getInstance().getCurrentRouteEntry();

        // update current entry
        updateGeoJson(entry);

        // create tileMap
        addImage(entry);


        // bind tableView to list
        logDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        logDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        logDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        logTable.setItems(LogMediator.getInstance().getLogList());
    }

    @Override
    public void updateRouteList(RouteEntry entry) {

    }

    @Override
    public void getCurrentRoute(RouteEntry currentRoute) {

    }

    @Override
    public boolean checkUniqueEntry(String givenEntryName) {
        return false;
    }

    public void updateTourLogList(LogEntry entry){
        LogMediator.getInstance().addEntry(entry);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Method to make list items clickable

    private void setTableItemClickEvent() {
        logTable.setOnMouseClicked(event -> {
            LogEntry selectedItem = (LogEntry) logTable.getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                // call Listener function for RouteMenuController
                LogMediator.getInstance().setCurrentLog(selectedItem);
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // UI interaction methods

    public void deleteEntry () throws IOException {

        if (LogMediator.getInstance().getCurrentLogEntry() != null){
            //get selected Entry and call methode from viewModel via LogMediator
            LogEntry selectedItem = (LogEntry) logTable.getSelectionModel().getSelectedItem();
            LogMediator.getInstance().deleteEntry(selectedItem.getId());

            //deselect current Route
            LogMediator.getInstance().deselectCurrentLog();

            System.out.println("Log with id: '" + selectedItem.getId() + "' deleted");
        }
        else {
            System.out.println("You have select a log entry before deleting");
        }


    }

    public void loadCreateTourLogWindow(ActionEvent event) {
        System.out.println("load route Log creation window");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/fhtw/tourPlanner/createTourLog.fxml"));
            Parent root = loader.load();
            CreateTourLogController controller = loader.getController();

            Stage popupStage = new Stage();
            popupStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            popupStage.initModality(Modality.WINDOW_MODAL);


            //**************** Frage an Prof ob das so passt  *****************************
            //popupStage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            controller.setStage(popupStage);

            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEditTourLogWindow(ActionEvent event){
        if (LogMediator.getInstance().getCurrentLogEntry() != null){
            System.out.println("load route Log creation window");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/at/fhtw/tourPlanner/editTourLog.fxml"));
                Parent root = loader.load();
                EditTourLogController controller = loader.getController();

                Stage popupStage = new Stage();
                popupStage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                popupStage.initModality(Modality.WINDOW_MODAL);


                //**************** Frage an Prof ob das so passt  *****************************
                //popupStage.initStyle(StageStyle.UNDECORATED);

                Scene scene = new Scene(root);
                popupStage.setScene(scene);

                controller.setStage(popupStage);

                popupStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("You have select a log entry before editing");
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // methods concerning tileMap

    private void updateGeoJson(RouteEntry entry){
        openrouteService.updateDirections(entry);
    }

    private JsonNode getGeoJson(RouteEntry entry){
        return openrouteService.getDirectionsGeoJson(entry);
    }

    private List<Double> getBbox(JsonNode geoJson){
        return openrouteService.getDirectionsBbox(entry);
    }

    private void addImage(RouteEntry entry){
        JsonNode geoJson = getGeoJson(entry);
        List<Double> bbox = getBbox(geoJson);

        osmService.setZoom(17);
        osmService.getMarkers().add(new OsmService.GeoCoordinate(bbox.get(0), bbox.get(1)));
        osmService.getMarkers().add(new OsmService.GeoCoordinate(bbox.get(2), bbox.get(3)));

        osmService.generateImage(bbox.get(0), bbox.get(1),bbox.get(2), bbox.get(3));

        //osmService.saveImage("fhtw-map.png");
        Image image = SwingFXUtils.toFXImage(osmService.getImage(), null);

        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.fitWidthProperty().bind(this.imgPane.widthProperty());
        imageView.setFitHeight(USE_COMPUTED_SIZE);

        this.imgPane.getChildren().add(imageView);

        osmService.saveImage("test.png");
    }
}
