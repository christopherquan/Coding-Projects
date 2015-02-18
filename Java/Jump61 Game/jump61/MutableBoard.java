
package jump61;

import static jump61.Side.*;
import static jump61.Square.square;
import java.util.ArrayList;
import java.util.Stack;

/** A Jump61 board state that may be modified.
 *  @author Christopher Quan
 */
class MutableBoard extends Board {

    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _size = N;
        _moves = 0;
        _history = new Stack<MutableBoard>();
        _board = new ArrayList<Square>(N * N);
        for (int i = 0; i < _size * _size; i++) {
            _board.add(i, Square.INITIAL);
        }
    }

    /** A board whose initial contents are copied from BOARD0, but whose
     *  undo history is clear. */
    MutableBoard(Board board0) {
        _moves = 0;
        _history = new Stack<MutableBoard>();
        _size = board0.size();
        _board = new ArrayList<Square>(_size * _size);
        for (int i = 0; i < _size * _size; i++) {
            _board.add(i, board0.get(i));
        }
    }

    @Override
    void clear(int N) {
        _moves = 0;
        _history = new Stack<MutableBoard>();
        _board = new ArrayList<Square>(N * N);
        _size = N;
        for (int i = 0; i < _size * _size; i++) {
            _board.add(i, Square.square(WHITE, 1));
        }
        announce();
    }

    @Override
    void copy(Board board) {
        _moves = 0;
        _history = new Stack<MutableBoard>();
        _size = board.size();
        _board = new ArrayList<Square>(_size * _size);
        for (int i = 0; i < _size * _size; i++) {
            _board.add(i, board.get(i));
        }
    }

    /** Copy the contents of BOARD into me, without modifying my undo
     *  history.  Assumes BOARD and I have the same size. */
    private void internalCopy(MutableBoard board) {
        for (int i = 0; i < _size * _size; i++) {
            internalSet(i, board.get(i));
        }
    }

    @Override
    int size() {
        return _size;
    }

    @Override
    Square get(int n) {
        return _board.get(n);
    }

    @Override
    int numOfSide(Side side) {
        int num = 0;
        for (Square x : _board) {
            if (x.getSide().equals(side)) {
                num++;
            }
        }
        return num;
    }

    @Override
    int numPieces() {
        int num = 0;
        for (Square x : _board) {
            num += x.getSpots();
        }
        return num;
    }

    @Override
    void addSpot(Side player, int r, int c) {
        int sq = (c - 1) + (r - 1) * size();
        addSpot(player, sq);
    }

    @Override
    void addSpot(Side player, int n) {
        markUndo();
        if (!isLegal(player) || !isLegal(player, n)) {
            return;
        }
        int spot = _board.get(n).getSpots() + 1;
        if (spot > neighbors(n)) {
            set(n, spot - neighbors(n), player);
            overfull(player, n);
        } else {
            internalSet(n, Square.square(player, spot));
        }
        announce();
    }

    /** Add a spot to the neighbor of a overfull square for PLAYER
     *  in row R and column C. */
    private void addSpotOverfull(Side player, int r, int c) {
        if (!exists(r, c)) {
            return;
        }
        int n = (c - 1) + (r - 1) * size();
        int spot = get(n).getSpots() + 1;
        if (spot > neighbors(n)) {
            set(n, spot - neighbors(n), player);
            overfull(player, n);
        } else {
            internalSet(n, Square.square(player, spot));
        }
        announce();
    }

    /** Adds spots to neighbors of square N for PLAYER. */
    private void overfull(Side player, int n) {
        if (getWinner() != null) {
            return;
        }
        int r = row(n);
        int c = col(n);
        addSpotOverfull(player, r - 1, c);
        addSpotOverfull(player, r + 1, c);
        addSpotOverfull(player, r, c - 1);
        addSpotOverfull(player, r, c + 1);
    }

    @Override
    void set(int r, int c, int num, Side player) {
        internalSet(sqNum(r, c), square(player, num));
    }

    @Override
    void set(int n, int num, Side player) {
        internalSet(n, square(player, num));
        announce();
    }

    @Override
    void undo() {
        internalCopy(_history.pop());
    }

    /** Record the beginning of a move in the undo history. */
    private void markUndo() {
        MutableBoard m = new MutableBoard(this);
        _history.push(m);
        _moves += 1;
    }

    /** Set the contents of the square with index IND to SQ. Update counts
     *  of numbers of squares of each color.  */
    private void internalSet(int ind, Square sq) {
        _board.set(ind, sq);
    }

    /** Notify all Observers of a change. */
    private void announce() {
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MutableBoard)) {
            return obj.equals(this);
        } else {
            if (obj.hashCode() == hashCode()) {
                return true;
            }
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /** Size of the board in terms of SIZExSIZE. */
    private int _size;

    /** ArrayList of strings that stores the board values. */
    private ArrayList<Square> _board;

    /** Stack that stores history of moves. */
    private Stack<MutableBoard> _history;

    /** Stores the number of moves made. */
    private int _moves;
}
