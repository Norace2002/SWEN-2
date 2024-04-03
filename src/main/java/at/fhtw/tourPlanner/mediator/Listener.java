package at.fhtw.tourPlanner.mediator;

import at.fhtw.tourPlanner.model.RouteEntry;

public interface Listener {
    void updateRouteList(RouteEntry entry);

    void getCurrentRoute(RouteEntry currentRoute);
}