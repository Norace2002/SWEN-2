package at.fhtw.tourPlanner.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RouteEntry implements Entry{
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
    private float time;
    //private Image mapImage;

    private final FloatProperty startLatitude;
    private final FloatProperty startLongitude;
    private final FloatProperty destinationLatitude;
    private final FloatProperty destinationLongitude;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // constructor
    public RouteEntry(String name, String description, String start, String destination, String transportType, float startLatitude, float startLongitude, float destinationLatitude, float destinationLongitude){
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.start = new SimpleStringProperty(start);
        this.destination = new SimpleStringProperty(destination);
        this.transportType = new SimpleStringProperty(transportType);
        this.startLatitude = new SimpleFloatProperty(startLatitude);
        this.startLongitude = new SimpleFloatProperty(startLongitude);
        this.destinationLatitude = new SimpleFloatProperty(destinationLatitude);
        this.destinationLongitude = new SimpleFloatProperty(destinationLongitude);
    }

    public RouteEntry(StringProperty name, StringProperty description, StringProperty start, StringProperty destination, StringProperty transportType, SimpleFloatProperty startLatitude, SimpleFloatProperty startLongitude, SimpleFloatProperty destinationLatitude, SimpleFloatProperty destinationLongitude) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.destination = destination;
        this.transportType = transportType;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
    }

    //Standard Constructor to convert json in Model
    public RouteEntry() {
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.start = new SimpleStringProperty("");
        this.destination = new SimpleStringProperty("");
        this.transportType = new SimpleStringProperty("");
        this.startLatitude = new SimpleFloatProperty(0.0f);
        this.startLongitude = new SimpleFloatProperty(0.0f);
        this.destinationLatitude = new SimpleFloatProperty(0.0f);
        this.destinationLongitude = new SimpleFloatProperty(0.0f);
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

    //Map Properties
    public float getStartLatitude() {
        return startLatitude.get();
    }

    public FloatProperty startLatitudeProperty() {
        return startLatitude;
    }

    public void setStartLatitude(float startLatitude) {
        this.startLatitude.set(startLatitude);
    }
    public float getStartLongitude() {return startLongitude.get();}

    public FloatProperty startLongitudeProperty() {
        return startLongitude;
    }

    public void setStartLongitude(float startLongitude) {
        this.startLongitude.set(startLongitude);
    }
    public float getDestinationLatitude() {
        return destinationLatitude.get();
    }

    public FloatProperty destinationLatitudeProperty() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(float startLatitude) {
        this.destinationLatitude.set(startLatitude);
    }
    public float getDestinationLongitude() {return destinationLongitude.get();}

    public FloatProperty destinationLongitudeProperty() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(float destinationLongitude) {this.destinationLongitude.set(destinationLongitude);}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REST call on create

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        // rest call for distance
        this.distance = distance;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float estimatedTime) {
        // rest call for estimated time
        this.time = estimatedTime;
    }

    /*public Image getMapImage() {
        return mapImage;
    }

    public void setMapImage(Image mapImage) {
        // rest call for image
        this.mapImage = mapImage;
    }

     */
}
