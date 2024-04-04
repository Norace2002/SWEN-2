package at.fhtw.tourPlanner.viewmodel;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel {
    private final ObservableList<String> routeEntries = FXCollections.observableArrayList();
    private final Map<String, RouteEntry> entryMap = new HashMap<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ObservableList<String> getRouteEntries(){return routeEntries;}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // functions related routeEntries

    public void addNewRouteEntry(RouteEntry newEntry){
        // add new entry object to map
        entryMap.put(newEntry.getName(), newEntry);

        // add new entry to listview
        routeEntries.add(newEntry.getName());

        // make database call
        /* newEntry.addToDatabase();
        System.out.println("routeentry added to db");
         */
    }

    public RouteEntry getRouteEntryByName(String routeName){
        return entryMap.get(routeName);
    }

    public void deleteRouteEntry(String entryName){

        // make database call - Delete
        /*
            RouteEntry entry = entryMap.get(entryName);
            entry.removeFromDatabase();
            System.out.println("routeentry removed from db");

         */

        // remove Entry via Entry name
        entryMap.remove(entryName);
        routeEntries.remove(entryName);


    }

}
