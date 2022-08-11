package sol;

import com.sun.javafx.geom.Edge;
import src.City;
import src.IBFS;
import src.IGraph;
import src.Transport;
import sun.security.provider.certpath.Vertex;

import java.util.*;

public class BFS<V, E> implements IBFS<V, E> {

    private HashMap<V, E> parents = new HashMap<V, E>();
    private IGraph<V, E> graph;




    // TODO: implement the getPath method!

    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        this.graph = graph;
        List<E> path = new ArrayList();
        if (canReach(start, end)) {
            V currentCity = end;
            while (currentCity != start) {
                E edge = this.parents.get(currentCity);
                V sourceCity = this.graph.getEdgeSource(edge);
                path.add(0, edge);
                currentCity = sourceCity;
            }
        }
        return path;
    }

    public Boolean canReach(V start, V end) {
        HashSet<E> visited = new HashSet<E>();
        LinkedList<E> toCheck = new LinkedList<E>(this.graph.getOutgoingEdges(start));
        while (!toCheck.isEmpty()) {
            E edge = toCheck.removeFirst();
            System.out.println(edge);
            if (visited.contains(edge)) {
                continue;
            }
            visited.add(edge);
            V target = this.graph.getEdgeTarget(edge);
            if (!this.parents.containsKey(target)) {
                this.parents.put(target, edge);
            }
            if (target.equals(end)) {
                return true;
            }
            else {
                toCheck.addAll(this.graph.getOutgoingEdges(target));
            }

        }
        return false;
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
}
