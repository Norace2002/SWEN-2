package at.fhtw.tourPlanner.viewmodel;

import at.fhtw.tourPlanner.backend.LogService;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.java.Log;

import java.io.IOException;

public class LogViewModel {

    private LogService logService = new LogService();
    private final ObservableList<LogEntry> logList = FXCollections.observableArrayList();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ObservableList<LogEntry> getLogList(){
        return this.logList;
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
