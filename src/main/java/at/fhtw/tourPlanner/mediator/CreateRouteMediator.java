package at.fhtw.tourPlanner.mediator;

public class CreateRouteMediator implements CreateRouteMediatorInterface {
    private String routeName;
    private String routeDescription;
    private String routeStart;
    private String routeDestination;
    private String routeTransportationType;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void copyRouteName(String name){
        this.routeName = name;
    }

    public void copyRouteDescription(String description){
        this.routeDescription = description;
    }

    public void copyRouteStart(String start){
        this.routeStart = start;
    }

    public void copyRouteDestination(String destination){
        this.routeDestination = destination;
    }

    public void copyRouteTransportationType(String transportType){
        this.routeTransportationType = transportType;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String relayRouteName(){
        return(this.routeName);
    }

    public String relayRouteDescription(){
        return(this.routeDescription);
    }

    public String relayRouteStart(){
        return(this.routeStart);
    }

    public String relayRouteDestination(){
        return(this.routeDestination);
    }

    public String relayRouteTransportationType(){
        return(this.routeTransportationType);
    }
}
