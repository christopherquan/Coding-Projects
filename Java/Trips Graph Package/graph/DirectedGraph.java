package graph;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Christopher Quan
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
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
