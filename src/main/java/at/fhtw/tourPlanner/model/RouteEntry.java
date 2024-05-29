package at.fhtw.tourPlanner.model;

import javafx.beans.property.*;

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
    private double distance;
    private double time;

    //private Image mapImage;

    private final DoubleProperty startLatitude;
    private final DoubleProperty startLongitude;
    private final DoubleProperty destinationLatitude;
    private final DoubleProperty destinationLongitude;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // constructor
    public RouteEntry(String name, String description, String start, String destination, String transportType, double startLatitude, double startLongitude, double destinationLatitude, double destinationLongitude){
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.start = new SimpleStringProperty(start);
        this.destination = new SimpleStringProperty(destination);
        this.transportType = new SimpleStringProperty(transportType);
        this.startLatitude = new SimpleDoubleProperty(startLatitude);
        this.startLongitude = new SimpleDoubleProperty(startLongitude);
        this.destinationLatitude = new SimpleDoubleProperty(destinationLatitude);
        this.destinationLongitude = new SimpleDoubleProperty(destinationLongitude);
    }

    public RouteEntry(StringProperty name, StringProperty description, StringProperty start, StringProperty destination, StringProperty transportType, SimpleDoubleProperty startLatitude, SimpleDoubleProperty startLongitude, SimpleDoubleProperty destinationLatitude, SimpleDoubleProperty destinationLongitude) {
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
        this.startLatitude = new SimpleDoubleProperty(0.0f);
        this.startLongitude = new SimpleDoubleProperty(0.0f);
        this.destinationLatitude = new SimpleDoubleProperty(0.0f);
        this.destinationLongitude = new SimpleDoubleProperty(0.0f);
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
    public double getStartLatitude() {
        return startLatitude.get();
    }

    public DoubleProperty startLatitudeProperty() {
        return startLatitude;
    }

    public void setStartLatitude(Double startLatitude) {
        this.startLatitude.set(startLatitude);
    }
    public double getStartLongitude() {return startLongitude.get();}

    public DoubleProperty startLongitudeProperty() {
        return startLongitude;
    }

    public void setStartLongitude(Double startLongitude) {
        this.startLongitude.set(startLongitude);
    }
    public double getDestinationLatitude() {
        return destinationLatitude.get();
    }

    public DoubleProperty destinationLatitudeProperty() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(Double startLatitude) {
        this.destinationLatitude.set(startLatitude);
    }
    public double getDestinationLongitude() {return destinationLongitude.get();}

    public DoubleProperty destinationLongitudeProperty() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(Double destinationLongitude) {this.destinationLongitude.set(destinationLongitude);}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // REST call on create

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        // rest call for distance
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double estimatedTime) {
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
