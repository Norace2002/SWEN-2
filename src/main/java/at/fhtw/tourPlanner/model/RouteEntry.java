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
    private StringProperty description;
    private StringProperty start;
    private StringProperty destination;
    private StringProperty transportType;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // retrieved from REST-call to https://openrouteservice.org/dev , https://tile.openstreetmap.org/
    private float distance;
    private float estimatedTime;
    private Image mapImage;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // constructor
    public RouteEntry(String name){
        this.name = new SimpleStringProperty(name);
    }

    public RouteEntry(StringProperty name) {
        this.name = name;
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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(float estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Image getMapImage() {
        return mapImage;
    }

    public void setMapImage(Image mapImage) {
        this.mapImage = mapImage;
    }
}
