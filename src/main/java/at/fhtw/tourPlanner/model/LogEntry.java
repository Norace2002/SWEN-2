package at.fhtw.tourPlanner.model;

import javafx.beans.property.*;

public class LogEntry implements Entry{
    private int id;
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty comment;
    private final IntegerProperty difficulty; // 1-5;
    private final DoubleProperty distance;
    private final DoubleProperty duration;
    private final IntegerProperty rating; // 1-5

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // constructor

    public LogEntry(int id, String date, String time, String comment, int difficulty, double distance, double duration, int rating){
        this.id = id;
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.comment = new SimpleStringProperty(comment);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.distance = new SimpleDoubleProperty(distance);
        this.duration = new SimpleDoubleProperty(duration);
        this.rating = new SimpleIntegerProperty(rating);
    }

    public LogEntry(StringProperty date, StringProperty time, StringProperty comment, IntegerProperty difficulty, DoubleProperty distance, DoubleProperty duration, IntegerProperty rating) {
        this.date = date;
        this.time = time;
        this.comment = comment;
        this.difficulty = difficulty;
        this.distance = distance;
        this.duration = duration;
        this.rating = rating;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // getter + setter

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

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
        return id;
    }

    public void setId(int newId){
        this.id = newId;
    }
}
