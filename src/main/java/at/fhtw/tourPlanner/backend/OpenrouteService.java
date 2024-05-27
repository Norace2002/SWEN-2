package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.geometry.BoundingBox;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenrouteService extends BaseService{

    private String apiKey = "5b3ce3597851110001cf624834b6ec4afd3d488695e036bb86a0e318";
    private HttpClient client = HttpClient.newHttpClient();
    private JsonNode directionsGeoJson;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // public methods, accesible from VM and Controllers

    public List<Double> getStartCoordinates(RouteEntry entry){
        JsonNode root = search(entry.getStart());
        return extractCoordinates(root);
    }

    public List<Double> getDestinationCoordinates(RouteEntry entry){
        JsonNode root = search(entry.getDestination());
        return extractCoordinates(root);
    }

    public Double getDuration(){
        return extractDuration(directionsGeoJson);
    }

    public Double getDistance(){
        return extractDistance(directionsGeoJson);
    }

    public void updateDirections(RouteEntry entry){
        List<Double> startCoordinates = Arrays.asList(entry.getStartLongitude(), entry.getStartLatitude());
        List<Double> destinationCoordinates = Arrays.asList(entry.getDestinationLongitude(), entry.getDestinationLatitude());

        this.directionsGeoJson = directions(entry.getTransportType(), startCoordinates, destinationCoordinates);
    }

    public JsonNode getDirectionsGeoJson(RouteEntry entry){
        return directionsGeoJson;
    }

    public List<Double> getDirectionsBbox(RouteEntry entry){
        return extractDirectionsBbox(directionsGeoJson);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    * GeocodeAPI:
    * responsible for resolving Location string input into coordinates
    * JSON formatted List
     */

    // return a JSON formatted list of objects corresponding to search input
    private JsonNode search(String input) {
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
            return getObjectMapper().readTree(response.body());
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * LocationAPI:
     * responsible for creating waypoints along the way between start and destination coordinates
     * GeoJSON format
     */

    private JsonNode directions(String transportType, List<Double> startCoordinates, List<Double> destinationCoordinates){
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
            return(getObjectMapper().readTree(response.body()));
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
            return null;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Helper functionalities
    // -> work by skimming through the json response structure and extracting the required values
    // -> very static

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

    // extract duration/distance from response with jackson
    private Double extractDuration(JsonNode rootNode){
        Double duration = 0.0;

        try {
            JsonNode features = rootNode.get("features");
            if (features.isArray() && !features.isEmpty()) {
                JsonNode firstFeature = features.get(0);
                JsonNode properties = firstFeature.get("properties");
                JsonNode segments = properties.get("segments");

                if (segments.isArray() && !segments.isEmpty()) {
                    JsonNode firstSegment = segments.get(0);
                    duration = firstSegment.get("duration").asDouble();
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return duration;
    }

    private Double extractDistance(JsonNode rootNode){
        Double distance = 0.0;

        try {
            JsonNode features = rootNode.get("features");
            if (features.isArray() && !features.isEmpty()) {
                JsonNode firstFeature = features.get(0);
                JsonNode properties = firstFeature.get("properties");
                JsonNode segments = properties.get("segments");

                if (segments.isArray() && !segments.isEmpty()) {
                    JsonNode firstSegment = segments.get(0);
                    distance = firstSegment.get("distance").asDouble();
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return distance;
    }

    // extract bbox from response with jackson
    private List<Double> extractDirectionsBbox(JsonNode rootNode){
        List<Double> bbox = new ArrayList<>();
        try {
            JsonNode bboxNode = rootNode.get("bbox");
            if (bboxNode.isArray() && bboxNode.size() == 4) {
                bbox.add(bboxNode.get(0).asDouble());
                bbox.add(bboxNode.get(1).asDouble());
                bbox.add(bboxNode.get(2).asDouble());
                bbox.add(bboxNode.get(3).asDouble());

                System.out.println(bbox);
                return bbox;
            } else{
                return null;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return null;
    }
}
