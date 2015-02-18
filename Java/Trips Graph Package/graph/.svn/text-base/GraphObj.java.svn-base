package graph;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Christopher Quan
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        _vertices = new HashMap<Integer, Vertex>();
        _edgeIDs = new HashMap<Side, Integer>();
        _edges = new HashSet<Side>();
    }

    /** Return the HashMap of vertices. */
    HashMap<Integer, Vertex> getVertices() {
        return _vertices;
    }

    @Override
    public int vertexSize() {
        return _vertices.size();
    }

    @Override
    public int maxVertex() {
        int max = 0;
        for (Integer i : _vertices.keySet()) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    @Override
    public int edgeSize() {
        return _edges.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        Vertex i = _vertices.get(v);
        if (i != null) {
            return i.numSuccessors();
        }
        return 0;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return _vertices.containsKey(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (contains(u) && contains(v)) {
            Vertex i = _vertices.get(u);
            return i.containsSucc(v);
        }
        return false;
    }

    @Override
    public int add() {
        int i = 1;
        while (_vertices.containsKey(i)) {
            i++;
        }
        _vertices.put(i, new Vertex(i));
        return i;
    }

    @Override
    public int add(int u, int v) {
        if (contains(u) && contains(v)) {
            Vertex i = _vertices.get(u);
            Vertex j = _vertices.get(v);
            i.addSucc(v);
            j.addPred(u);
            if (!isDirected() || u == v) {
                i.addPred(v);
                j.addSucc(u);
                if (!_edges.contains(new Side(v, u))) {
                    _edges.add(new Side(u, v));
                }
            } else {
                _edges.add(new Side(u, v));
            }
        }
        return u;
    }

    @Override
    public void remove(int v) {
        _vertices.remove(v);
        for (Vertex i : _vertices.values()) {
            i.remove(v);
        }
        HashSet<Side> rem = new HashSet<Side>();
        for (Side s : _edges) {
            if (s.contains(v)) {
                rem.add(s);
            }
        }
        for (Side t : rem) {
            _edges.remove(t);
        }
    }

    @Override
    public void remove(int u, int v) {
        if (contains(u) && contains(v)) {
            Vertex i = _vertices.get(u);
            Vertex j = _vertices.get(v);
            i.removeSucc(v);
            j.removePred(u);
            _edges.remove(new Side(u, v));
            if (!isDirected() || u == v) {
                i.removePred(v);
                j.removeSucc(u);
                _edges.remove(new Side(v, u));
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iteration<Integer> vertices() {
        ArrayList li = new ArrayList(_vertices.keySet());
        Collections.sort(li);
        return Iteration.iteration(li);
    }

    @Override
    public int successor(int v, int k) {
        if (contains(v)) {
            Vertex i = _vertices.get(v);
            return i.getSucc(k);
        }
        return 0;
    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        if (contains(v)) {
            Vertex i = _vertices.get(v);
            return Iteration.iteration(i.getSucc());
        }
        return Iteration.iteration(new ArrayList<Integer>());
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> i = new ArrayList<int[]>();
        for (Side s : _edges) {
            int[] a = s.convertArray();
            i.add(a);
        }
        return Iteration.iteration(i);
    }

    @Override
    protected boolean mine(int v) {
        return _vertices.containsKey(v);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!_vertices.containsKey(v)) {
            throw new IllegalArgumentException("Vertex does not exist");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        Side edge1 = new Side(u, v);
        Side edge2 = new Side(v, u);
        if (isDirected()) {
            if (_edgeIDs.containsKey(edge1)) {
                return _edgeIDs.get(edge1);
            }
        } else {
            if (_edgeIDs.containsKey(edge1)) {
                return _edgeIDs.get(edge1);
            } else if (_edgeIDs.containsKey(edge2)) {
                return _edgeIDs.get(edge2);
            }
        }
        int id = _edgeIDs.size() + 1;
        _edgeIDs.put(edge1, id);
        return id;
    }

    /** Class used to indicate sides in graph. */
    private class Side {
        /** Start of side. */
        private int _start;
        /** End of side. */
        private int _end;
        /** Number 71 used for hashcode. */
        private final int _seventyone = 71;

        /** Constructor for side with a START and END. */
        public Side(int start, int end) {
            _start = start;
            _end = end;
        }

        /** Return the start of a side. */
        int getStart() {
            return _start;
        }

        /** Return the end of a side. */
        int getEnd() {
            return _end;
        }

        /** Return boolean whether side contains vertex V. */
        boolean contains(int v) {
            return (getStart() == v) || (getEnd() == v);
        }

        /** Return int array of the side. */
        int[] convertArray() {
            int[] a = new int[2];
            a[0] = getStart();
            a[1] = getEnd();
            return a;
        }

        @Override
        public boolean equals(Object i) {
            if (i instanceof Side) {
                Side other = (Side) i;
                return ((other.getStart() == getStart())
                    && (other.getEnd() == getEnd()));
            }
            return false;
        }

        /** Hashcode taken from
         *  http://stackoverflow.com/questions/9135759/
         *  java-hashcode-for-a-point-class.
         *  Returns the hashcode of Side using 71.
         */
        @Override
        public int hashCode() {
            int hash = 7;
            hash = _seventyone * hash + getStart();
            hash = _seventyone * hash + getEnd();
            return hash;
        }
    }

    /** HashMap of the vertices. */
    private HashMap<Integer, Vertex> _vertices;

    /** HashMap of the edge IDs. */
    private HashMap<Side, Integer> _edgeIDs;

    /** HashSet of the edges. */
    private HashSet<Side> _edges;
}
