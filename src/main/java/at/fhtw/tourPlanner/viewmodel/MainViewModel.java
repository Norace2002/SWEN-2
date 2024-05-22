package at.fhtw.tourPlanner.viewmodel;
import at.fhtw.tourPlanner.backend.RouteService;
import at.fhtw.tourPlanner.backend.OpenrouteService;
import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
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
    private OpenrouteService openrouteService = new OpenrouteService();

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

        // set coordinates from location input
        this.setCoordinates(entry);

        // update directions with potential new coordinates
        this.updateDirections(entry);

        // set distance/duration from location input
        this.setDuration(entry);
        this.setDistance(entry);

        // check if entry with same name already exits and if so,  instead edit altered values in db
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void updateDirections(RouteEntry entry){
        openrouteService.updateDirections(entry);
    }

    private void setCoordinates(RouteEntry entry){
        // get openRoute values by API call
        List<Double> startCoordinates = openrouteService.getStartCoordinates(entry);
        List<Double> destinationCoordinates = openrouteService.getDestinationCoordinates(entry);

        try{
            entry.setStartLongitude(startCoordinates.get(0));
            entry.setStartLatitude(startCoordinates.get(1));

            entry.setDestinationLongitude(destinationCoordinates.get(0));
            entry.setDestinationLatitude(destinationCoordinates.get(1));

            /*
            System.out.println("Coordinates -> Start: "+ entry.getStartLatitude() +" | "+ entry.getStartLongitude() +
                    " + Destination: "+entry.getDestinationLatitude()+" | "+entry.getDestinationLongitude());
             */
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    private void setDuration(RouteEntry entry){
        try{
            entry.setTime(this.openrouteService.getDuration());
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    private void setDistance(RouteEntry entry){
        try{
            entry.setDistance(this.openrouteService.getDistance());
        }catch(RuntimeException e){
            e.printStackTrace();
        }
    }

}
