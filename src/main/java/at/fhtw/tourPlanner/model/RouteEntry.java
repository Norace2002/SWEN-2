package at.fhtw.tourPlanner.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RouteEntry {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // standard variables
    private final StringProperty name;
    private final StringProperty description;
    private final StringProperty start;
    private final StringProperty destination;
    private final StringProperty transportType;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // retrieved from REST-call to https://openrouteservice.org/dev , https://tile.openstreetmap.org/
    private float distance;
    private float estimatedTime;
    private Image mapImage;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // constructor
    public RouteEntry(String name, String description, String start, String destination, String transportType){
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.start = new SimpleStringProperty(start);
        this.destination = new SimpleStringProperty(destination);
        this.transportType = new SimpleStringProperty(transportType);
    }

    public RouteEntry(StringProperty name, StringProperty description, StringProperty start, StringProperty destination, StringProperty transportType) {
        this.name = name;
        this.description = name;
        this.start = name;
        this.destination = name;
        this.transportType = name;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getter + setter

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getStart() {
        return start.get();
    }

    public StringProperty startProperty() {
        return start;
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public String getDestination() {
        return destination.get();
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getTransportType() {
        return transportType.get();
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType.set(transportType);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REST call on create

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        // rest call for distance
        this.distance = distance;
    }

    public float getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(float estimatedTime) {
        // rest call for estimated time
        this.estimatedTime = estimatedTime;
    }

    public Image getMapImage() {
        return mapImage;
    }

    public void setMapImage(Image mapImage) {
        // rest call for image
        this.mapImage = mapImage;
    }
}
