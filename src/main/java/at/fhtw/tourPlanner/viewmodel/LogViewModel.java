package at.fhtw.tourPlanner.viewmodel;

import at.fhtw.tourPlanner.model.LogEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LogViewModel {
    private final ObservableList<LogEntry> logList = FXCollections.observableArrayList(
            new LogEntry(0,"test", "test", "test", 1, 20.2, 30.3, 1)
    );

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ObservableList<LogEntry> getLogList(){
        return this.logList;
    }

    public void addEntry(LogEntry entry){
        logList.add(entry);
    }

    public void deleteEntry(int id){
        for (LogEntry entry : logList) {
            // if the id is found - delete it
            if (entry.getId() == id) {
                logList.remove(entry);
                break;
            }
        }
    }

}
