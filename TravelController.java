package sol;

import src.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TravelController implements ITravelController<City, Transport> {

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not declared in IGraph?
    // Hint: perhaps you need to define a method!
    private TravelGraph graph;

    public TravelController() {
    }

    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        Function<Map<String, String>, Void> addEdge = map -> {
            Transport newEdge = new Transport(this.graph.getCity(map.get("origin")), this.graph.getCity(map.get("destination")),
                    TransportType.fromString(map.get("type")), Double.parseDouble(map.get("price")),
                    Double.parseDouble(map.get("duration")));
            return null; // need explicit return null to account for Void type
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);

        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }
        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseTransportation(transportFile, addEdge);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }
        return "Successfully loaded cities and transportation files.";
    }

    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Dijkstra<City, Transport> ourDijkstra = new Dijkstra<City, Transport>();
        Function<Transport, Double> fastFunction = transport -> {
            return transport.getMinutes(); // need explicit return null to account for Void type
        };
        return ourDijkstra.getShortestPath(this.graph, this.graph.getCity(source), this.graph.getCity(destination), fastFunction);

    }

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Dijkstra<City, Transport> ourDijkstra = new Dijkstra<City, Transport>();
        Function<Transport, Double> cheapFunction = transport -> {
            return transport.getPrice(); // need explicit return null to account for Void type
        };
        return ourDijkstra.getShortestPath(this.graph, this.graph.getCity(source), this.graph.getCity(destination), cheapFunction);

    }

    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        BFS<City, Transport> ourBFS = new BFS<City, Transport>();
        return ourBFS.getPath(this.graph, this.graph.getCity(source), this.graph.getCity(destination));
    }

    public IGraph<City, Transport> getGraph() {
        return this.graph;
    }
}
