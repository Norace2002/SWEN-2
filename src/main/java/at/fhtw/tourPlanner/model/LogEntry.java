package at.fhtw.tourPlanner.model;

import javafx.beans.property.*;
import java.sql.Date;

public class LogEntry implements Entry{
    private final IntegerProperty id;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty comment;
    private final IntegerProperty difficulty; // 1-5;
    private final DoubleProperty distance;
    private final DoubleProperty duration;
    private final IntegerProperty rating; // 1-5
    private final StringProperty route;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // constructor

    public LogEntry(String date, String time, String comment,
                     int difficulty, double distance, double duration,
                     int rating, String route){
        this.id = new SimpleIntegerProperty(0);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.comment = new SimpleStringProperty(comment);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.distance = new SimpleDoubleProperty(distance);
        this.duration = new SimpleDoubleProperty(duration);
        this.rating = new SimpleIntegerProperty(rating);
        this.route = new SimpleStringProperty(route);
    }

    //Standard Constructor to convert json in Model
    public LogEntry(){
        this.id = new SimpleIntegerProperty(0);
        this.date = new SimpleStringProperty("");
        this.time = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.difficulty = new SimpleIntegerProperty(0);
        this.distance = new SimpleDoubleProperty(0.0f);
        this.duration = new SimpleDoubleProperty(0.0f);
        this.rating = new SimpleIntegerProperty(0);
        this.route = new SimpleStringProperty("");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getter + setter

    public String getDate() {return date.get();}

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {return time.get();}

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {this.time.set(time);}

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public int getDifficulty() {
        return difficulty.get();
    }

    public IntegerProperty difficultyProperty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty.set(difficulty);
    }

    public double getDistance() {
        return distance.get();
    }

    public DoubleProperty distanceProperty() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance.set(distance);
    }

    public double getDuration() {
        return duration.get();
    }

    public DoubleProperty durationProperty() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration.set(duration);
    }

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public int getId(){
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int newId){
        this.id.set(newId);
    }

    public String getRoute(){return route.get();}

    public void setRoute(String route){this.route.set(route);}
}
