package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenrouteService extends BaseService{

    private String apiKey = "5b3ce3597851110001cf624834b6ec4afd3d488695e036bb86a0e318";
    private HttpClient client = HttpClient.newHttpClient();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Double> getStartCoordinates(RouteEntry entry){
        return search(entry.getStart());
    }

    public List<Double> getDestinationCoordinates(RouteEntry entry){
        return search(entry.getDestination());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    * GeocodeAPI:
    * responsible for resolving Location string input into coordinates
    * JSON formatted List
     */

    // return a JSON formatted list of objects corresponding to search input
    private List<Double> search(String input) {
        String encodedInput = URLEncoder.encode(input, StandardCharsets.UTF_8);
        String url = String.format("https://api.openrouteservice.org/geocode/search?api_key=%s&text=%s", apiKey, encodedInput);

        try{
            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response from server: " + response.body());

            // return results by handing over response as JSON node to getCoordinates function
            return extractCoordinates(getObjectMapper().readTree(response.body()));
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * LocationAPI:
     * responsible for creating waypoints along the way between start and destination coordinates
     * GeoJSON format
     */

    public JsonNode directions(String transportType, List<Double> startCoordinates, List<Double> destinationCoordinates){
        String url = String.format("https://api.openrouteservice.org/v2/directions/%s?api_key=%s&start=%s,%s&end=%s,%s",
                transportType,
                apiKey,
                startCoordinates.get(0), startCoordinates.get(1),
                destinationCoordinates.get(0), destinationCoordinates.get(1)
        );

        try{
            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response from server: " + response.body());

            // return results by handing over response as JSON node to getCoordinates function
            return getObjectMapper().readTree(response.body());
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper functionalities

    // extract coordinates from response values with jackson
    private List<Double> extractCoordinates(JsonNode rootNode){
        List<Double> results = new ArrayList<>();

        try{
            JsonNode featuresNode = rootNode.get("features");
            JsonNode firstFeature = featuresNode.get(0);
            JsonNode coordinatesNode = firstFeature.get("geometry").get("coordinates");
            if (coordinatesNode.isArray() && coordinatesNode.size() >= 2) {
                results.add(coordinatesNode.get(0).asDouble()); // Longitude
                results.add(coordinatesNode.get(1).asDouble()); // Latitude
            }
        }catch(RuntimeException e){
            e.printStackTrace();
        }

        return results;
    }
}
