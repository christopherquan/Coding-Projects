package graph;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.HashSet;
import java.util.LinkedList;

/* See restrictions in Graph.java. */

import java.util.List;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Christopher Quan
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        setWeight(_source, 0);
        HashSet<Integer> vert = new HashSet<Integer>();
        for (Integer i : _G.vertices()) {
            vert.add(i);
        }
        _traversal = new AStarTraversal(_G, new AStarQueue());
        _traversal.traverse(vert);
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.addFirst(v);
        int pred = v;
        while (pred != _source && pred != 0) {
            pred = getPredecessor(pred);
            list.addFirst(pred);
        }
        return list;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** A traversal class used to implement A*. */
    private class AStarTraversal extends Traversal {

        /** Creates an AStarTraversal with Graph G and Queue Q. */
        AStarTraversal(Graph g, Queue<Integer> q) {
            super(g, q);
        }

        @Override
        protected boolean processSuccessor(int u, int v) {
            double newWeight = getWeight(u) + getWeight(u, v);
            if (newWeight < getWeight(v)) {
                setWeight(v, newWeight);
                setPredecessor(v, u);
                HashSet<Integer> remove = new HashSet<Integer>();
                int i = -1;
                while (i != v) {
                    i = _fringe.remove();
                    remove.add(i);
                }
                for (int j : remove) {
                    _fringe.add(j);
                }
            }
            return false;
        }

        @Override
        protected boolean visit(int v) {
            return v != _dest;
        }

        @Override
        protected boolean shouldPostVisit(int v) {
            return false;
        }
    }

    /** A queue class used to implement A*. */
    private class AStarQueue extends PriorityQueue<Integer> {

        /** Create an AStarQueue. */
        public AStarQueue() {
            super(_G.vertexSize(), _compare);
        }

    }

    /** New Comparator for AStarQueue. */
    public class VertexComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer x, Integer y) {
            double xval;
            double yval;
            if (getWeight(x) != Double.MAX_VALUE
                && estimatedDistance(x) != Double.MAX_VALUE) {
                xval = getWeight(x) + estimatedDistance(x);
            } else {
                xval = Double.MAX_VALUE;
            }
            if (getWeight(y) != Double.MAX_VALUE
                && estimatedDistance(y) != Double.MAX_VALUE) {
                yval = getWeight(y) + estimatedDistance(y);
            } else {
                yval = Double.MAX_VALUE;
            }
            if (xval > yval) {
                return 1;
            }
            if (xval < yval) {
                return -1;
            }
            return 0;
        }
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** New Comparator for vertices. */
    private VertexComparator _compare = new VertexComparator();
    /** AStar traversal. */
    private AStarTraversal _traversal;
}
