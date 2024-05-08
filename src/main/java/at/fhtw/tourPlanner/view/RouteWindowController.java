package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.LogMediator;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.viewmodel.LogViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

//libraries to keep popups in bounds
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RouteWindowController implements Initializable, Listener {
    private RouteEntry entry;

    @FXML
    private TableView logTable;
    @FXML
    private TableColumn logDate;
    @FXML
    private TableColumn logDuration;
    @FXML
    private TableColumn logDistance;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // interface methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableItemClickEvent();

        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);

        // get currently chosen entry
        entry = Mediator.getInstance().getCurrentRouteEntry();

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


        //get selected Entry and call methode from viewModel via LogMediator
        LogEntry selectedItem = (LogEntry) logTable.getSelectionModel().getSelectedItem();
        LogMediator.getInstance().deleteEntry(selectedItem.getId());

        //deselect current Route
        LogMediator.getInstance().deselectCurrentLog();

        System.out.println("Log with id: '" + selectedItem.getId() + "' deleted");

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
    }
}
