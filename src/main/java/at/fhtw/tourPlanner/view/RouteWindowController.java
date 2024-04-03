package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.mediator.Listener;
import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.viewmodel.LogViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class RouteWindowController implements Initializable, Listener {
    private RouteEntry entry;
    private LogViewModel viewModel = new LogViewModel();

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
        // subscribe to Mediator
        Mediator.getInstance().registerListener(this);

        // get currently chosen entry
        entry = Mediator.getInstance().getCurrentRouteEntry();

        // bind tableView to list
        logDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        logDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        logDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        logTable.setItems(viewModel.getLogList());
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
