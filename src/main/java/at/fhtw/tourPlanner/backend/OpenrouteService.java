package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenrouteService extends BaseService{

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
            System.out.println("Response from server: " + response.body());

            List<Double> bbox = getObjectMapper().readValue(response.body(), new TypeReference<List<Double>>() {});

            return bbox;
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
