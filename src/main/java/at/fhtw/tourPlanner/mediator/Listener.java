package at.fhtw.tourPlanner.mediator;

import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.model.LogEntry;

public interface Listener {
    void updateRouteList(RouteEntry entry);

    void getCurrentRoute(RouteEntry currentRoute);

    boolean checkUniqueEntry(String givenEntryName);

    void updateTourLogList(LogEntry entry);

}
