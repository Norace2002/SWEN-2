package at.fhtw.tourPlanner.view;

import at.fhtw.tourPlanner.listener.FocusChangedListener;
import at.fhtw.tourPlanner.viewmodel.MainViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final MainViewModel viewModel = new MainViewModel();

    // references used to setup data-binding
    @FXML
    Pane hostPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void loadFxml(ActionEvent event){
        try{
            Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("path/to/file.xml"));
            hostPane.getChildren().add(newLoadedPane);
        } catch(IOException e){
            System.out.println("Problem loading FXML into Pane");
            e.printStackTrace();
        }
    }
}
