package graph;
import java.util.HashMap;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Christopher Quan
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
    }

    @Override
    public double getWeight(int v) {
        if (_weights.containsKey(v)) {
            return _weights.get(v);
        }
        return Double.MAX_VALUE;
    }

    @Override
    protected void setWeight(int v, double w) {
        _weights.put(v, w);
    }

    @Override
    public int getPredecessor(int v) {
        if (_predecessors.containsKey(v)) {
            return _predecessors.get(v);
        }
        return 0;
    }

    @Override
    protected void setPredecessor(int v, int u) {
        _predecessors.put(v, u);
    }

    /** HashMap that store weight values. */
    private HashMap<Integer, Double> _weights =
        new HashMap<Integer, Double>(_G.vertexSize());
    /** HasMap that store predecessors. */
    private HashMap<Integer, Integer> _predecessors =
        new HashMap<Integer, Integer>(_G.vertexSize());

}
