package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.model.RouteEntry;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ReportService extends BaseService{
    public byte[] getRouteReport(RouteEntry entry) {
        try {
            String name = entry.getName();

            String url = "http://localhost:8080/report/" + name;

            // Create an HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public byte[] getSummaryReport() {
        try {

            String url = "http://localhost:8080/report/summary";

            // Create an HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
