package at.fhtw.tourPlanner.viewmodel;

import at.fhtw.tourPlanner.listener.FocusChangedListener;
import at.fhtw.tourPlanner.model.example_HighscoreEntry;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class example_MainViewModel {
    private List<FocusChangedListener> focusChangedListenerList = new ArrayList<FocusChangedListener>();

    private final StringProperty currentUsername = new SimpleStringProperty("");
    private final StringProperty currentPoints = new SimpleStringProperty("");
    private final ObservableList<example_HighscoreEntry> data =
            FXCollections.observableArrayList(
                    new example_HighscoreEntry("daniel", "infinite"),
                    new example_HighscoreEntry("not daniel", "few")
            );

    public StringProperty getCurrentUsername() {
        return currentUsername;
    }

    public StringProperty getCurrentPoints(){
        return currentPoints;
    }

    public ObservableList<example_HighscoreEntry> getData(){
        return data;
    }

    public void addListener(FocusChangedListener listener) {
        this.focusChangedListenerList.add(listener);
    }

    public void saveDataToList(){
        data.add(new example_HighscoreEntry(currentUsername.get(), currentPoints.get()));
        currentUsername.set("");
        currentPoints.set("");
        for (var listener: this.focusChangedListenerList) {
            listener.requestFocusChange("input of username");
        }
    }
}
