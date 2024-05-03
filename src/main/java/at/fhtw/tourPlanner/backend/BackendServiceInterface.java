package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.Entry;

import java.io.IOException;

public interface BackendServiceInterface {
    Entry getEntry(Entry entry) throws IOException;

    void addEntry(Entry entry) throws IOException;

    void deleteEntry(Entry entry) throws IOException;

    void editEntry(Entry entry) throws IOException;
}
