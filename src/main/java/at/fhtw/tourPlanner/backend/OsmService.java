package at.fhtw.tourPlanner.backend;

import at.fhtw.tourPlanner.Main;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OsmService extends BaseService{

    public record Tile(int x, int y) {}
    public record Point(int x, int y) {}
    public record GeoCoordinate(double lon, double lat) {}

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public enum Marker {
        PIN_RED_16px("pin-red_16px"),
        PIN_RED_32px("pin-red_32px"),
        MARKER_RED_16px("marker-red_16px"),
        MARKER_RED_32px( "marker-red_32px");

        Marker(String filename) {
            this.filename = filename;
        }

        public final String filename;

        public URL getResource() {
            URL resourceUrl = Main.class.getResource(Marker.PIN_RED_32px.filename + ".png");
            if (resourceUrl == null) {
                System.err.println("Resource not found: " + Marker.PIN_RED_32px.filename + ".png");
            }
            return resourceUrl;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Setter
    private int zoom = 17;
    @Setter
    private boolean cropImage = true;

    private final List<GeoCoordinate> markers = new ArrayList<>();

    private BufferedImage finalImage;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // private helper methods to convert doubles into necessary structures
    private static Tile latlon2Tile(double lat_deg, double lon_deg, int zoom) {
        double lat_rad = Math.toRadians(lat_deg);
        double n = Math.pow(2.0, zoom);
        int x_tile = (int) Math.floor((lon_deg + 180.0) / 360.0 * n);
        int y_tile = (int) Math.floor((1.0 - Math.log(Math.tan(lat_rad) + 1 / Math.cos(lat_rad)) / Math.PI) / 2.0 * n);
        return new Tile(x_tile, y_tile);
    }

    public static Point latLonToPixel(double lat, double lon, int zoom) {
        double lat_rad = Math.toRadians(lat);
        double n = Math.pow(2.0, zoom);
        int x_pixel = (int) Math.floor((lon + 180.0) / 360.0 * n * 256);
        int y_pixel = (int) Math.floor((1.0 - Math.log(Math.tan(lat_rad) + 1 / Math.cos(lat_rad)) / Math.PI) / 2.0 * n * 256);

        return new Point(x_pixel, y_pixel);
    }

    // fetches Tile from openstreetmap service
    private static BufferedImage fetchTile(int zoom, int x, int y) throws IOException {
        String tileUrl = "https://tile.openstreetmap.org/" + zoom + "/" + x + "/" + y + ".png";
        URL url = new URL(tileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.addRequestProperty("User-Agent", "CarSharingTest/1.0 Demo application for the SAM-course");

        try (InputStream inputStream = httpConn.getInputStream()) {
            return ImageIO.read(inputStream);
        } finally {
            httpConn.disconnect();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // generate image from coordinates

    public void generateImage(double minLon, double minLat, double maxLon, double maxLat){
        try{
            // Calculate the tile numbers for each corner of the bounding box
            var topLeftTile = latlon2Tile(maxLat, minLon, zoom);
            var bottomRightTile = latlon2Tile(minLat, maxLon, zoom);

            // Determine the number of tiles to fetch in each dimension
            int tilesX = bottomRightTile.x() - topLeftTile.x() + 1;
            int tilesY = bottomRightTile.y() - topLeftTile.y() + 1;

            // Create a new image to hold all the tiles
            finalImage = new BufferedImage(tilesX * 256, tilesY * 256, BufferedImage.TYPE_INT_ARGB);
            Graphics g = finalImage.getGraphics();

            // Fetch and draw each tile
            for (int x = topLeftTile.x(); x <= bottomRightTile.x(); x++) {
                for (int y = topLeftTile.y(); y <= bottomRightTile.y(); y++) {
                    BufferedImage tileImage = fetchTile(zoom, x, y);
                    int xPos = (x - topLeftTile.x()) * 256;
                    int yPos = (y - topLeftTile.y()) * 256;
                    g.drawImage(tileImage, xPos, yPos, null);
                }
            }

            Point topLeftTilePixel = new Point( topLeftTile.x() * 256, topLeftTile.y() * 256 );

            // Draw Markers
            for ( var marker : markers ) {
                BufferedImage markerIcon = ImageIO.read( Marker.PIN_RED_32px.getResource() );
                Point globalPos = latLonToPixel(marker.lat(), marker.lon(), zoom);
                Point relativePos = new Point(globalPos.x() - topLeftTilePixel.x(), globalPos.y() - topLeftTilePixel.y() );
                g.drawImage(markerIcon, relativePos.x(), relativePos.y(), null);
            }

            // Crop the image to the exact bounding box
            if ( cropImage ) {
                Point bboxLeftTopGlobalPos = latLonToPixel(maxLat, minLon, zoom);
                Point bboxRightBottomGlobalPos = latLonToPixel(minLat, maxLon, zoom);
                Point bboxLeftTopRelativePos = new Point(bboxLeftTopGlobalPos.x() - topLeftTilePixel.x(), bboxLeftTopGlobalPos.y() - topLeftTilePixel.y());
                int width = bboxRightBottomGlobalPos.x() - bboxLeftTopGlobalPos.x();
                int height = bboxRightBottomGlobalPos.y() - bboxLeftTopGlobalPos.y();
                finalImage = finalImage.getSubimage(bboxLeftTopRelativePos.x(), bboxLeftTopRelativePos.y(), width, height);
            }

            g.dispose();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // store image (testing only)
    public void saveImage(String filename) {
        try{
            // Save or manipulate the final image as needed
            ImageIO.write(finalImage, "png", new File(filename));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(){
        return finalImage;
    }

}
