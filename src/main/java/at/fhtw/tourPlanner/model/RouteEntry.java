package at.fhtw.tourPlanner.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RouteEntry {
    private final StringProperty routeName;

    public RouteEntry(String routeName){
        this.routeName = new SimpleStringProperty(routeName);
    }
    public RouteEntry(StringProperty routeName){
        this.routeName = routeName;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getRouteName() {
        return routeName.get();
    }

    public void setRouteName(String routeName) {
        this.routeName.set(routeName);
    }

    public StringProperty routeNameProperty() {
        return routeName;
    }
}
