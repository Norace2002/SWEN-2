package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.Entry;
import at.fhtw.tourPlanner.model.LogEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LogService implements BackendServiceInterface{

    @Override
    public Entry getEntry(Entry entry) throws IOException, InterruptedException{
        // create new Route Entry in backend
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
        System.out.println("Response from server: " + response.body());

        //Convert jackson into entry
        return null;
    }

    @Override
    public void addEntry(Entry entry)  throws IOException, InterruptedException{
        // create new Route Entry in backend
        String url = "http://localhost:8080/log";
        String json = "test";

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
        System.out.println("Response from server: " + response.body());

    }

    @Override
    public void deleteEntry(Entry entry) throws IOException, InterruptedException{
        // create new Route Entry in backend
        String url = "http://localhost:8080/log";

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

    @Override
    public void editEntry(Entry entry) throws IOException, InterruptedException{
        // create new Route Entry in backend
        String url = "http://localhost:8080/log";
        String json = "test";

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
        System.out.println("Response from server: " + response.body());

    }
}
