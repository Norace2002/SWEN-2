package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.Entry;
import at.fhtw.tourPlanner.model.LogEntry;
import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class LogService extends BaseService implements BackendServiceInterface{

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Logger Set up
    private static final Logger logger = LogManager.getLogger(LogService.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Entry getEntry(Entry entry) throws IOException, InterruptedException{
        // get specific log entry
        String url = "http://localhost:8080/log";

        // Create an HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response
        logger.info("Response from server: " + response.body() + " - Methode (GET/.../log)");

        //Convert jackson into entry
        return null;
    }

    public String getAllEntries() throws IOException, InterruptedException{
        String routeName = Mediator.getInstance().getCurrentRouteEntry().getName();

        // get all existing log entries from current route entry
        String url = "http://localhost:8080/log/all?route=" + URLEncoder.encode(routeName, StandardCharsets.UTF_8);

        // Create an HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response
        logger.info("Response from server: " + response.body() + " - Methode (GET/.../log/all/" + routeName + ")");

        //Convert jackson into entry
        return response.body();
    }

    @Override
    public String addEntry(Entry entry)  throws IOException, InterruptedException{
        // create new Route Entry in backend
        String url = "http://localhost:8080/log";

        // turn entry object into JSON string
        String json = this.entryToJson(entry);

        // Create an HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create a request - turn entry into jason string
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response
        logger.info("Response from server: " + response.body() + " - Methode (POST/.../log)");

        return response.body();
    }

    @Override
    public void deleteEntry(Entry entry) throws IOException, InterruptedException{
        if (entry instanceof LogEntry) {
            // Casting von Entry zu RouteEntry to extract the name only
            LogEntry logEntry = (LogEntry) entry;

            // delete specific log entry
            String url = "http://localhost:8080/log?name=" + URLEncoder.encode(Integer.toString(logEntry.getId()), StandardCharsets.UTF_8);

            // Create an HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            logger.info("Response from server: " + response.body() + " - Methode (DELETE/.../log/" + logEntry.getId() + ")");
        } else {
            logger.error("Problem occurred while passing entry from type LogEntry");
        }
    }

    @Override
    public void editEntry(Entry entry) throws IOException, InterruptedException{
        // edit specific log entry
        String url = "http://localhost:8080/log";
        String json = this.entryToJson(entry);

        // Create an HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response
        logger.info("Response from server: " + response.body() + " - Methode (PUT/.../log)");

    }
}
