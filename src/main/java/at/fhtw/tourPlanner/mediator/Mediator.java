package at.fhtw.tourPlanner.mediator;

import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.model.LogEntry;

import java.util.ArrayList;

/*
* Implementing the Mediator as a Singleton with listeners is a solution hinted to us by our colleague.
* We implemented the functionality without further assistance, yet we assume a certain similarity is bound to occur.
* Credits: Adrian Andretsch
 */

public class Mediator{

    private RouteEntry currentRoute;
    private LogEntry currentLog;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Singleton implementation
    private static Mediator instance;
    private Mediator(){};

    public static Mediator getInstance(){
        if(instance == null){
            instance = new Mediator();
        }
        return instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // event

    ArrayList<Listener> listeners = new ArrayList<Listener>();

    public RouteEntry getCurrentRouteEntry(){
        return currentRoute;
    }
    public LogEntry getCurrentLogEntry(){return currentLog; }

    public void registerListener(Listener newListener){
        listeners.add(newListener);
    }

    public void publishRouteUpdate(RouteEntry entry){
        for(var listener : listeners){
            listener.updateRouteList(entry);
        }
    }

    public void publishLogEntry(LogEntry entry){
        for(var listener : listeners){
            listener.updateTourLogList(entry);
        }
    }


    public void setCurrentRoute(RouteEntry entry){
        currentRoute = entry;
        System.out.println("currentRoute set");
    }

    public void setCurrentLog(LogEntry entry){
        currentLog = entry;
        System.out.println("currentLog set");
    }

    public void publishCurrentRoute(){
        for(var listener : listeners){
            listener.getCurrentRoute(currentRoute);
        }

        System.out.println("all listeners received currentRoute");
    }

    public boolean checkUniqueRouteEntryIdentifier(String givenEntryName){
        for(var listener : listeners){
            if(listener.checkUniqueEntry(givenEntryName)) {
                return true;
            }
        }
        return false;
    }


    public void deselectCurrentRoute(){
        currentRoute = null;
    }

    public void deselectCurrentLog(){
        currentLog = null;
    }



}
