package graph;
import java.util.ArrayList;

/** Class that is used to represent vertices.
 *  @author Christopher Quan
 */
class Vertex {

    /** Value of the vertex. */
    private int _value;
    /** Predecessors of the vertex. */
    private ArrayList<Integer> _predecessors;
    /** Successors of the vertex. */
    private ArrayList<Integer> _successors;

    /** Constructor for for a vertex with value VALUE. */
    public Vertex(int value) {
        _value = value;
        _predecessors = new ArrayList<Integer>();
        _successors = new ArrayList<Integer>();
    }

    /** Returns number of successors. */
    int numSuccessors() {
        return _successors.size();
    }

    /** Returns number of predecessors. */
    int numPredecessors() {
        return _predecessors.size();
    }

    /** Return whether S is successor of vertex. */
    boolean containsSucc(int s) {
        return _successors.contains(s);
    }

    /** Add S to predecessors of vertex. */
    void addPred(int s) {
        if (!_predecessors.contains(s)) {
            _predecessors.add(s);
        }
    }

    /** Add S to successors of vertex. */
    void addSucc(int s) {
        if (!_successors.contains(s)) {
            _successors.add(s);
        }
    }

    /** Remove S from predecessors of vertex. */
    void removePred(int s) {
        _predecessors.remove((Integer) s);
    }

    /** Remove S from successors of vertex. */
    void removeSucc(int s) {
        _successors.remove((Integer) s);
    }

    /** Remove S from both successors and pred of vertex. */
    void remove(int s) {
        removePred(s);
        removeSucc(s);
    }

    /** Return the successor at index IND. */
    int getSucc(int ind) {
        if (ind < numSuccessors()) {
            return _successors.get(ind);
        }
        return 0;
    }

    /** Return the predecessor at index IND. */
    int getPred(int ind) {
        if (ind < numPredecessors()) {
            return _predecessors.get(ind);
        }
        return 0;
    }

    /** Return Arraylist of successors. */
    ArrayList<Integer> getSucc() {
        return _successors;
    }

    /** Return ArrayList of predecessors. */
    ArrayList<Integer> getPred() {
        return _predecessors;
    }

    /** Return Arraylist of outgoing sides in terms of arrays. */
    ArrayList<int[]> getSides() {
        ArrayList<int[]> i = new ArrayList<int[]>();
        for (int j : _successors) {
            int[] pair = new int[2];
            pair[0] = _value;
            pair[1] = j;
            i.add(pair);
        }
        return i;
    }
}
