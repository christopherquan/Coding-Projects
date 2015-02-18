package graph;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author Christopher Quan
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {
        Vertex i = getVertices().get(v);
        if (i != null) {
            return i.numPredecessors();
        }
        return 0;
    }

    @Override
    public int predecessor(int v, int k) {
        if (contains(v)) {
            Vertex i = getVertices().get(v);
            return i.getPred(k);
        }
        return 0;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        if (contains(v)) {
            Vertex i = getVertices().get(v);
            return Iteration.iteration(i.getPred());
        }
        return Iteration.iteration(new ArrayList<Integer>());
    }
}
