package at.fhtw.tourPlanner.viewmodel;

import at.fhtw.tourPlanner.backend.LogService;
import at.fhtw.tourPlanner.model.LogEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.java.Log;

import java.io.IOException;

public class LogViewModel {

    private LogService logService = new LogService();
    private final ObservableList<LogEntry> logList = FXCollections.observableArrayList(
            new LogEntry(0,"test", "test", "test", 1, 20.2, 30.3, 1)
    );

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ObservableList<LogEntry> getLogList(){
        return this.logList;
    }

    public void addEntry(LogEntry entry){
        // add entry to DB
        try{
            logService.addEntry(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // add entry to LogList
        logList.add(entry);
    }

    public void deleteEntry(int id) throws IOException {
        for (LogEntry entry : logList) {
            // if the id is found - delete it
            if (entry.getId() == id) {
                // remove entry from DB
                logService.deleteEntry(entry);

                // remove entry from list
                logList.remove(entry);
                break;
            }
        }
    }

}
