package at.fhtw.tourPlanner.mediator;

public interface CreateRouteMediatorInterface {
    void copyRouteName(String name);
    void copyRouteDescription(String description);
    void copyRouteStart(String start);
    void copyRouteDestination(String destination);
    void copyRouteTransportationType(String transportType);
    String relayRouteName();
    String relayRouteDescription();
    String relayRouteStart();
    String relayRouteDestination();
    String relayRouteTransportationType();
}
