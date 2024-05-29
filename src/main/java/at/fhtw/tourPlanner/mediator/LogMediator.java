package at.fhtw.tourPlanner.mediator;

import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.viewmodel.LogViewModel;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

//LogMediator manages every communication to the viewModel
public class LogMediator {

    private LogEntry currentLog;

    private final LogViewModel viewModel = new LogViewModel();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Singleton implementation
    private static LogMediator instance;
    private LogMediator(){};

    public static LogMediator getInstance(){
        if(instance == null){
            instance = new LogMediator();
        }
        return instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public LogEntry getCurrentLogEntry(){return currentLog; }

    public void setCurrentLog(LogEntry entry){
        currentLog = entry;
        System.out.println("currentLog set");
    }

    public void deselectCurrentLog(){
        currentLog = null;
    }

    public ObservableList<LogEntry>  getLogList(){
        return viewModel.getLogList();
    }

    // -------------- Db fun --------------
    public void getEntry(LogEntry entry){
        viewModel.getEntry(entry);
    }

    public int addEntry(LogEntry entry){
        return viewModel.addEntry(entry);
    }

    public void deleteEntry(int entryID){
        try{
            viewModel.deleteEntry(entryID);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void editEntry(LogEntry entry){
        viewModel.editEntry(entry);
    }





}
