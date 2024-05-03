package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.Entry;
import at.fhtw.tourPlanner.model.RouteEntry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class RouteService implements BackendServiceInterface{

    private void establishConnection(String requestMethode) throws IOException{
        // create new Route Entry in backend
        String url = "http://localhost:8080/route";

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        // Set the request method to GET
        connection.setRequestMethod(requestMethode);

        // Read the response of the request
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            System.out.println("Response from server: " + response.toString());
        } finally {
            // Disconnect the connection
            connection.disconnect();
        }
    }

    @Override
    public Entry getEntry(Entry entry) throws IOException{
        establishConnection("GET");

        return null;
    }

    @Override
    public void addEntry(Entry entry)  throws IOException{
        establishConnection("POST");
    }

    @Override
    public void deleteEntry(Entry entry) throws IOException{
        establishConnection("DELETE");
    }

    @Override
    public void editEntry(Entry entry) throws IOException{
        establishConnection("PUT");
    }
}
