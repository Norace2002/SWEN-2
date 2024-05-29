package at.fhtw.tourPlanner.viewmodel;

import at.fhtw.tourPlanner.backend.LogService;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;

public class LogViewModel {

    private LogService logService = new LogService();
    private final ObservableList<LogEntry> logList = FXCollections.observableArrayList();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Object Mapper
    @Getter
    private ObjectMapper objectMapper;

    public List<LogEntry> jsonToModel(String json){
        try{
            this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.readValue(json, new TypeReference<List<LogEntry>>(){});
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ObservableList<LogEntry> getLogList(){
        try {
            // Clear all existing entries in logList
            logList.clear();

            //try to get entries form db
            String json = logService.getAllEntries();

            //Convert to observableList
            List<LogEntry> logEntryList = jsonToModel(json);
            logList.addAll(logEntryList);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return logList;
    }

    public void getEntry(LogEntry entry){
        // get entry from DB
        try{
            logService.getEntry(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int addEntry(LogEntry entry){
        String serverResponse = "";

        // add entry to DB
        try{
            serverResponse = logService.addEntry(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // add entry to LogList
        logList.add(entry);

        return Integer.parseInt(serverResponse);
    }

    public void deleteEntry(int id) throws IOException {
        for (LogEntry entry : logList) {
            // if the id is found - delete it
            if (entry.getId() == id) {
                // remove entry from DB
                try{
                    logService.deleteEntry(entry);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                // remove entry from list
                logList.remove(entry);
                break;
            }
        }
    }

    public void editEntry(LogEntry entry){
        // edit entry in DB
        try{
            logService.editEntry(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
