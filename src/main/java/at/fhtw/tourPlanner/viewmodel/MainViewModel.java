package at.fhtw.tourPlanner.viewmodel;

import at.fhtw.tourPlanner.listener.FocusChangedListener;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel {
    private List<FocusChangedListener> focusChangedListenerList = new ArrayList<FocusChangedListener>();
    private final ObservableList<RouteEntry> routeEntries =
            FXCollections.observableArrayList(
                    new RouteEntry("Wienerwald", "", "", "", ""),
                    new RouteEntry("Dopllerhuette", "", "", "", ""),
                    new RouteEntry("Figlwarte", "", "", "", "")
            );

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // functions

    public ObservableList<RouteEntry> getRouteEntries(){
        return routeEntries;
    }

    public void addNewRouteEntryToList(String name, String description, String start, String destination, String transportType){
        // take over variables from input
        RouteEntry entry = new RouteEntry(name,description,start,destination,transportType);

        // calculate values

        // add entry to list
        routeEntries.add(entry);
    }

}
