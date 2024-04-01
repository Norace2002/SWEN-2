package at.fhtw.tourPlanner.mediator;

import at.fhtw.tourPlanner.model.RouteEntry;

import java.util.ArrayList;

public class Mediator{
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

    public void routePicked(RouteEntry entry){
        for(var listener : listeners){
            listener.loadRouteInformation(entry);
        }
    }

}
