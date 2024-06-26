package at.fhtw.tourPlanner.viewmodel;
import at.fhtw.tourPlanner.backend.RouteService;
import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class MainViewModel {
    private final ObservableList<String> routeEntries = FXCollections.observableArrayList();
    private Map<String, RouteEntry> entryMap = new HashMap<>(); //final?
    private RouteService routeService = new RouteService();




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Object Mapper
    @Getter
    private ObjectMapper objectMapper;

    public List<RouteEntry> jsonToModel(String json){
        try{
            this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.readValue(json, new TypeReference<List<RouteEntry>>(){});
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ObservableList<String> getRouteEntries(){

        try {
            // Clear all existing entries in routeEntries/entryMap
            routeEntries.clear();
            entryMap.clear();

            //try to get entries form db
            String json = routeService.getAllEntries();



            List<RouteEntry> routeEntryList = jsonToModel(json);
            //Copy everything into entryMap
            for (RouteEntry entry : routeEntryList) {
                entryMap.put(entry.getName(), entry);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Add entries to observableList
        routeEntries.addAll(entryMap.keySet());

        return routeEntries;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // functions related routeEntries

    public void updateRouteEntries(RouteEntry entry){

        //check if entry with same name already exits and if so,  instead edit altered values in db

        if(entryMap.containsKey(entry.getName())){
            //edit values from existing routeEntry
            try {
                routeService.editEntry(entry);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            //If entry is new add it to Map/Listview
            entryMap.put(entry.getName(), entry);
            routeEntries.add(entry.getName());

            // add Route to DB through backend service
            try {
                routeService.addEntry(entry);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
