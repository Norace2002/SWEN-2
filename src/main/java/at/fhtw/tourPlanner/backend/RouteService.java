package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.mediator.Mediator;
import at.fhtw.tourPlanner.model.Entry;
import at.fhtw.tourPlanner.model.RouteEntry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RouteService extends BaseService implements BackendServiceInterface{

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Logger Set up
    private static final Logger logger = LogManager.getLogger(RouteService.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Entry getEntry(Entry entry) throws IOException, InterruptedException{
        // get specific route entry
        String url = "http://localhost:8080/route";

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
        logger.info("Response from server: " + response.statusCode() + " - Methode (GET/.../route)");

        //--- Convert jackson into entry - we currently don't need getEntry ---
        return null;
    }

    public String getAllEntries() throws IOException, InterruptedException{
        // get all existing route entries
        String url = "http://localhost:8080/route/all";

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
        logger.info("Response from server: " + response.statusCode() +  " - Methode (GET/.../route/all)");

        //Convert jackson into entry
        return response.body();
    }

    @Override
    public String addEntry(Entry entry)  throws IOException, InterruptedException{
        // create new Route Entry in backend
        String url = "http://localhost:8080/route";
        String json = this.entryToJson(entry);

        // Create an HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create a request - turn entry into jason string
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(json))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response
        logger.info("Response from server : successfully created entry - Methode (POST/.../route)");

        //Because addEntry is an Interface method and LogService needs to return id - function can't be void
        return "";
    }

    @Override
    public void deleteEntry(Entry entry) throws IOException, InterruptedException{

        if (entry instanceof RouteEntry) {
            // Casting von Entry zu RouteEntry to extract the name only
            RouteEntry routeEntry = (RouteEntry) entry;

            // delete specific route entry
            String url = "http://localhost:8080/route?name=" + URLEncoder.encode(routeEntry.getName(), StandardCharsets.UTF_8);

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
            logger.info("Response from server: " + response.body() + " - Methode (DELETE/.../route/ " + routeEntry.getName() + ")");
        }
        else {
            logger.error("Problem occurred while passing entry from type RouteEntry");
        }


    }

    @Override
    public void editEntry(Entry entry) throws IOException, InterruptedException{
        // edit specific route entry
        String url = "http://localhost:8080/route";
        String json = this.entryToJson(entry);

        // Create an HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Create a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(BodyPublishers.ofString(json))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response
        System.out.println("Response from server: " + response.body() + " - Methode (PUT/.../route)");

    }
}
