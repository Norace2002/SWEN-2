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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RouteService extends BaseService implements BackendServiceInterface{

    @Override
    public Entry getEntry(Entry entry){
        try{
            // get specific route entry
            String url = "http://localhost:8080/route/"+ entry.getIdentifier();

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
            System.out.println("Response from server: " + response.body());

            //Convert jackson into entry
            RouteEntry dbEntry = getObjectMapper().readValue(response.body(), new TypeReference<RouteEntry>() {});
            return dbEntry;
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
            return new RouteEntry();
        }
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
        System.out.println("Response from server: " + response.body());

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
        System.out.println("Response from server : successfully created entry");

        return "";
    }

    @Override
    public void deleteEntry(Entry entry) throws IOException, InterruptedException{

        if (entry instanceof RouteEntry) {
            // Casting von Entry zu RouteEntry to extract the name only
            RouteEntry routeEntry = (RouteEntry) entry;

            // delete specific route entry
            String url = "http://localhost:8080/route/" + entry.getIdentifier();

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
            System.out.println("Response from server: " + response.body());
        }
        else {
            System.out.println("Problem occurred while passing entry from type RouteEntry");
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
        System.out.println("Response from server: " + response.body());

    }
}
