package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.*;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {

    private IGraph<V, E> graph;
    private HashMap<V, E> parents;
    private HashMap<V, Double> distances;
    private Function<E, Double> edgeWeight;
    private Comparator<V> compare;



    // TODO: implement the getShortestPath method!
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        this.graph = graph;
        this.parents = new HashMap<V, E>();
        this.distances = new HashMap<V, Double>();
        this.edgeWeight = edgeWeight;
        this.compare = (v1, v2) -> {
            return this.distances.get(v1).compareTo(this.distances.get(v2));
        };
        for (V vertex : this.graph.getVertices()) {
            this.distances.put(vertex, Double.MAX_VALUE);
        }
        this.computeDistances(source);
        return this.pathHelper(destination);
    }

    private void computeDistances(V source) {
        this.distances.put(source, 0.0);
        PriorityQueue<V> queue = new PriorityQueue(this.compare);
        queue.addAll(this.graph.getVertices());
        while (!queue.isEmpty()) {
            V currentV = queue.poll();
            for (E edge : this.graph.getOutgoingEdges(currentV)) {
                V target = this.graph.getEdgeTarget(edge);
                Double edgeWeight = this.edgeWeight.apply(edge);
                Double distance = this.distances.get(currentV) + edgeWeight;
                if (distance < this.distances.get(target)) {
                    this.distances.put(target, distance);
                    this.parents.put(target, edge);
                }
            }
        }
    }

    private List<E> pathHelper(V destination) {
        List<E> path = new ArrayList<E>();
        V currentV = destination;
        E previousE = this.parents.get(currentV);
        while (previousE != null) {
            path.add(0, previousE);
            currentV = this.graph.getEdgeSource(previousE);
            previousE = this.parents.get(currentV);
        }
        return path;
    }

    // TODO: feel free to add your own methods here!
}
