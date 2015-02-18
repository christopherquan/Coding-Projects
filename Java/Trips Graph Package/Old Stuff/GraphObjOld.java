package graph;
import java.util.HashSet;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Christopher Quan
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        // FIXME
        _vertices = new HashSet<Integer>();
        _sides = new ArrayList<Side>();
    }

    @Override
    public int vertexSize() {
        // FIXME
        return _vertices.size();
    }

    @Override
    public int maxVertex() {
        // FIXME
        int max = 0;
        for (Integer i : _vertices) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    @Override
    public int edgeSize() {
        // FIXME
        return _sides.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        // FIXME
        int num = 0;
        for (Side i : _sides) {
            if (i.getStart() == v) {
                num++;
            }
        }
        return num;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        // FIXME
        return _vertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        // FIXME
        if (isDirected()) {
            for (Side i : _sides) {
                if (i.getStart() == u && i.getEnd() == v) {
                    return true;
                }
            }
        } else {
            for (Side i : _sides) {
                if ((i.getStart() == u && i.getEnd() == v)
                    || (i.getStart() == v && i.getEnd() == u)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int add() {
        int i = 1;
        while (_vertices.contains(i)) {
            i++;
        }
        return i;
    }

    @Override
    public int add(int u, int v) {
        Side x = new Side(u, v);
        Side y = new Side(v, u);
        if (isDirected()) {
            if (!_sides.contains(x)) {
                _sides.add(x);
            }
        } else {
            if (!_sides.contains(x) && !_sides.contains(y)) {
                _sides.add(x);
            }
        }
        return u;
    }

    @Override
    public void remove(int v) {
        // FIXME
        _vertices.remove(v);
        for (Side i : _sides) {
            if (i.getStart() == v || i.getEnd() == v) {
                _sides.remove(i);
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        // FIXME
        Side x = new Side(u, v);
        Side y = new Side(v, u);
        if (isDirected()) {
            _sides.remove(x);
        } else {
            _sides.remove(x);
            _sides.remove(y);
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        // FIXME
        return null;
    }

    @Override
    public int successor(int v, int k) {
        // FIXME
        return 0;
    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        // FIXME
        return null;
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        // FIXME
        return null;
    }

    @Override
    protected boolean mine(int v) {
        // FIXME
        return false;
    }

    @Override
    protected void checkMyVertex(int v) {
        // FIXME
    }

    @Override
    protected int edgeId(int u, int v) {
        // FIXME
        return 0;
    }

    // FIXME
    private HashSet<Integer> _vertices;
    private ArrayList<Side> _sides;

}
