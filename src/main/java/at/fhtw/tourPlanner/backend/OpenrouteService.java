package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.application.HostServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenrouteService extends BaseService{


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Logger Set up
    private static final Logger logger = LogManager.getLogger(OpenrouteService.class);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public List<Double> getDirectionsBbox(RouteEntry entry){
        try{
            String name = entry.getName();

            String url = "http://localhost:8080/directions/"+name;

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
            logger.info("Response from server: " + response.body());

            List<Double> bbox = getObjectMapper().readValue(response.body(), new TypeReference<List<Double>>() {});

            return bbox;
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public String getDirections(RouteEntry entry){
        try{
            String name = entry.getName();

            String url = "http://localhost:8080/directions/webview/"+name;

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
            logger.info("Response from server: " + response.statusCode());

            return response.body();
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

        return "";
    }
}
