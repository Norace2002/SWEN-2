package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.Entry;

import java.io.IOException;

public interface BackendServiceInterface {
    Entry getEntry(Entry entry) throws IOException, InterruptedException;

    String getAllEntries() throws IOException, InterruptedException;

    String addEntry(Entry entry) throws IOException, InterruptedException;

    void deleteEntry(Entry entry) throws IOException,InterruptedException;

    void editEntry(Entry entry) throws IOException, InterruptedException;
}
