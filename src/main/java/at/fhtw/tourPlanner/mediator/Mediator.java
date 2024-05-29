package at.fhtw.tourPlanner.mediator;

import at.fhtw.tourPlanner.model.RouteEntry;

import at.fhtw.tourPlanner.model.LogEntry;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

/*
* Implementing the Mediator as a Singleton with listeners is a solution hinted to us by our colleague.
* We implemented the functionality without further assistance, yet we assume a certain similarity is bound to occur.
* Credits: Adrian Andretsch
 */

public class Mediator{

    private RouteEntry currentRoute;

    private Listener listener;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Singleton implementation
    private static Mediator instance;
    private Mediator(){};


    public void registerListener(Listener newListener){
        listener  = newListener;
    }


    public static Mediator getInstance(){
        if(instance == null){
            instance = new Mediator();
        }
        return instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Basic functionality
    public RouteEntry getCurrentRouteEntry(){
        return currentRoute;
    }

    public void setCurrentRoute(RouteEntry entry){
        currentRoute = entry;
        System.out.println("currentRoute set");
    }

    public void deselectCurrentRoute(){
        currentRoute = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // events

    //creates or edits a route depending on whether there is an existing entry in the db or not
    public void publishRouteUpdate(RouteEntry entry){
        listener.updateRouteList(entry);
    }


    public boolean checkUniqueRouteEntryIdentifier(String givenEntryName){
        return listener.checkUniqueEntry(givenEntryName);
    }

}
