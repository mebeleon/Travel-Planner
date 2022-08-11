package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {

    private HashMap<String, City> cityMap;

    public TravelGraph() {
        this.cityMap = new HashMap<String, City>();
    }

    @Override
    public void addVertex(City vertex) {
        this.cityMap.put(vertex.toString(), vertex);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        origin.addOut(edge);
    }

    @Override
    public Set<City> getVertices() {
        return new HashSet<>(this.cityMap.values());
    }

    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    public City getCity(String name) {
        if (this.cityMap.get(name) == null) {
            throw new IllegalArgumentException();
        }
        else return this.cityMap.get(name);
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
}