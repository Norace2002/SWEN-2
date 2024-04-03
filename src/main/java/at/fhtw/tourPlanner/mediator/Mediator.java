package at.fhtw.tourPlanner.mediator;

import at.fhtw.tourPlanner.model.RouteEntry;

import java.util.ArrayList;

/*
* Implementing the Mediator as a Singleton with listeners is a solution hinted to us by our colleague.
* We implemented the functionality without further assistance, yet we assume a certain similarity is bound to occur.
* Credits: Adrian Andretsch
 */

public class Mediator{

    private RouteEntry currentRoute;

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

    public void registerListener(Listener newListener){
        listeners.add(newListener);
    }

    public void publishRouteUpdate(RouteEntry entry){
        for(var listener : listeners){
            listener.updateRouteList(entry);
        }
    }

    public void setCurrentRoute(RouteEntry entry){
        currentRoute = entry;
        System.out.println("currentRoute set");
    }

    /* Frage an Prof:
    * Umsetzung von get currentRoute als Publish für alle User oder als getter?
    */

    public void publishCurrentRoute(){
        for(var listener : listeners){
            listener.getCurrentRoute(currentRoute);
        }

        System.out.println("all listeners received currentRoute");
    }

    public RouteEntry getCurrentRouteEntry(){
        return currentRoute;
    }

}
