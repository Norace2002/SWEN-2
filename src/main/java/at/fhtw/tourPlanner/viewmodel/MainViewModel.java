package at.fhtw.tourPlanner.viewmodel;

import at.fhtw.tourPlanner.listener.FocusChangedListener;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel {
    private List<FocusChangedListener> focusChangedListenerList = new ArrayList<FocusChangedListener>();
    private final StringProperty name = new SimpleStringProperty("");
    private final ObservableList<RouteEntry> data =
            FXCollections.observableArrayList(
                    new RouteEntry("Wienerwald"),
                    new RouteEntry("Dopllerhuette"),
                    new RouteEntry("Figlwarte"),
                    new RouteEntry("Dorfrunde")
            );

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // functions

    public StringProperty getName() {
        return name;
    }

    public ObservableList<RouteEntry> getData(){
        return data;
    }

    public void addListener(FocusChangedListener listener) {
        this.focusChangedListenerList.add(listener);
    }

    public void saveDataToList(){
        data.add(new RouteEntry(name.get()));
        name.set("");
        for (var listener: this.focusChangedListenerList) {
            listener.requestFocusChange("input of routename");
        }
    }

}
