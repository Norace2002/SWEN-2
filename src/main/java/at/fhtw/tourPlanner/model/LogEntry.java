package at.fhtw.tourPlanner.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class LogEntry {
    private final StringProperty date;
    private final StringProperty time;
    private final StringProperty comment;
    private final IntegerProperty difficulty; // 1-5;
    private final FloatProperty distance;
    private final FloatProperty duration;
    private final IntegerProperty rating; // 1-5

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // constructor

    public LogEntry(StringProperty date, StringProperty time, StringProperty comment, IntegerProperty difficulty, FloatProperty distance, FloatProperty duration, IntegerProperty rating) {
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

    public float getDistance() {
        return distance.get();
    }

    public FloatProperty distanceProperty() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance.set(distance);
    }

    public float getDuration() {
        return duration.get();
    }

    public FloatProperty durationProperty() {
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
}
