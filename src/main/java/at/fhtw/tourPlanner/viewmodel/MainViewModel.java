package at.fhtw.tourPlanner.viewmodel;
import at.fhtw.tourPlanner.backend.LogService;
import at.fhtw.tourPlanner.backend.RouteService;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class MainViewModel {
    private final ObservableList<String> routeEntries = FXCollections.observableArrayList();
    private Map<String, RouteEntry> entryMap = new HashMap<>(); //final?
    private RouteService routeService = new RouteService();
    private LogService logService = new LogService();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Object Mapper

    @Getter
    private ObjectMapper objectMapper;

    public RouteEntry routeJsonToModel(String json){
        try{
            return this.objectMapper.readValue(json, new TypeReference<RouteEntry>(){});
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public List<RouteEntry> routeJsonListToModel(String json){
        try{
            this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.readValue(json, new TypeReference<List<RouteEntry>>(){});
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public String routeModelToJson(RouteEntry model){
        try{
            return this.objectMapper.writeValueAsString(model);
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public List<LogEntry> logJsonListToModel(String json){
        try{
            this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.readValue(json, new TypeReference<List<LogEntry>>(){});
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



            List<RouteEntry> routeEntryList = routeJsonListToModel(json);
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

    public void updateRouteEntries(RouteEntry entry) {
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
            // add Route to DB through backend service
            try {
                routeService.addEntry(entry);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //If entry is new add it to Map/Listview
            RouteEntry dbEntry = (RouteEntry) routeService.getEntry(entry);

            entryMap.put(dbEntry.getName(), dbEntry);
            routeEntries.add(dbEntry.getName());
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
    // full text search feature

    public ObservableList<String> filterRoutes(String query){
        if(Objects.equals(query, "") || query == null){
            return routeEntries;
        }

        ObservableList<String> filteredEntries = FXCollections.observableArrayList();
        Map<String, RouteEntry> filteredEntryMap = new HashMap<>();

        // iterate over entryMap and look for potential matches
        for(RouteEntry route : entryMap.values()){
            if(matchingQuery(route, query)){
                filteredEntryMap.put(route.getName(), route);
                filteredEntries.add(route.getName());
            }
        }

        return filteredEntries;
    }

    public boolean matchingQuery(RouteEntry entry, String query){
        if(matchingRouteContent(entry, query)){
            // match route contents first
            return true;
        }
        else{
            // if route contents dont match look for log content
            for(LogEntry log : getLogList(entry)){
                if(matchingLogContent(log, query)){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean matchingRouteContent(RouteEntry entry, String query){
        // matching route data
        if(entry.getName().contains(query)){
            return true;
        }
        else if(entry.getDescription().contains(query)){
            return true;
        }
        else if(entry.getStart().contains(query)){
            return true;
        }
        else if(entry.getDestination().contains(query)){
            return true;
        }
        else if(Integer.toString(entry.getChildFriendliness()).contains(query)){
            return true;
        }
        else if(Integer.toString(entry.getPopularity()).contains(query)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean matchingLogContent(LogEntry log, String query){
        if (Integer.toString(log.getId()).contains(query)) {
            return true;
        }
        else if (log.getDate().contains(query)) {
            return true;
        }
        else if (log.getTime().contains(query)) {
            return true;
        }
        else if (log.getComment().contains(query)) {
            return true;
        }
        else if (Integer.toString(log.getDifficulty()).contains(query)) {
            return true;
        }
        else if (Double.toString(log.getDistance()).contains(query)) {
            return true;
        }
        else if (Double.toString(log.getDuration()).contains(query)) {
            return true;
        }
        else if (Integer.toString(log.getRating()).contains(query)) {
            return true;
        }
        else if (log.getRoute().contains(query)) {
            return true;
        }
        else {
            return false;
        }
    }

    public List<LogEntry> getLogList(RouteEntry route){
        List<LogEntry> logEntryList = new ArrayList<>();

        try {
            //get log entries from db
            String json = logService.getEntriesPerRoute(route.getName());
            logEntryList = logJsonListToModel(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return logEntryList;
    }
}
