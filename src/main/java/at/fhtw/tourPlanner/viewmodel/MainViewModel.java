package at.fhtw.tourPlanner.viewmodel;
import at.fhtw.tourPlanner.backend.RouteService;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewModel {
    private final ObservableList<String> routeEntries = FXCollections.observableArrayList();
    private final Map<String, RouteEntry> entryMap = new HashMap<>();
    private RouteService routeService = new RouteService();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ObservableList<String> getRouteEntries(){return routeEntries;}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // functions related routeEntries

    public void addNewRouteEntry(RouteEntry newEntry){
        // add new entry object to map
        entryMap.put(newEntry.getName(), newEntry);

        // add new entry to listview
        routeEntries.add(newEntry.getName());

        // add Route to DB through backend service
        try {
            routeService.addEntry(newEntry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public RouteEntry getRouteEntryByName(String routeName){
        return entryMap.get(routeName);
    }

    public void deleteRouteEntry(String entryName){

        // make database call - Delete
        RouteEntry entry = entryMap.get(entryName);
        try{
            routeService.deleteEntry(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // remove Entry via Entry name
        entryMap.remove(entryName);
        routeEntries.remove(entryName);
    }

}
