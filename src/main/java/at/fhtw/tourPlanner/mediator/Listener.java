package at.fhtw.tourPlanner.mediator;

import at.fhtw.tourPlanner.model.RouteEntry;
import at.fhtw.tourPlanner.model.LogEntry;

public interface Listener {
    void updateRouteList(RouteEntry entry);

    boolean checkUniqueEntry(String givenEntryName);

}
