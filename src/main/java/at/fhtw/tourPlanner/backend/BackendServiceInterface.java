package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.Entry;

import java.io.IOException;

public interface BackendServiceInterface {
    Entry getEntry(Entry entry);

    void addEntry(Entry entry) throws IOException;

    void deleteEntry(Entry entry);

    void editEntry(Entry entry);
}
