
package jump61;

import java.util.ArrayList;
import static jump61.Side.*;

/** An automated Player.
 *  @author Christopher Quan
 */
class AI extends Player {

    /** Time allotted to all but final search depth (milliseconds). */
    private static final long TIME_LIMIT = 15000;

    /** Number of calls to minmax between checks of elapsed time. */
    private static final long TIME_CHECK_INTERVAL = 10000;

    /** Number of milliseconds in one second. */
    private static final double MILLIS = 1000.0;

    /** A new player of GAME initially playing COLOR that chooses
     *  moves automatically.
     */
    public AI(Game game, Side color) {
        super(game, color);
    }

    @Override
    void makeMove() {
        ArrayList<Integer> move = new ArrayList<Integer>(1);
        int score = minmax(getSide(), getBoard(), 4,
            Integer.MAX_VALUE, move);
        getGame().makeMove(move.get(0));
        int x = move.get(0);
        getGame().reportMove(getSide(), getBoard().row(x), getBoard().col(x));
    }

    /** Return the minimum of CUTOFF and the minmax value of board B
     *  (which must be mutable) for player P to a search depth of D
     *  (where D == 0 denotes statically evaluating just the next move).
     *  If MOVES is not null and CUTOFF is not exceeded, set MOVES to
     *  a list of all highest-scoring moves for P; clear it if
     *  non-null and CUTOFF is exceeded. the contents of B are
     *  invariant over this call. */
    private int minmax(Side p, Board b, int d, int cutoff,
     ArrayList<Integer> moves) {
        int x = -1 * (b.size() * b.size() + 2);
        int bestMove = 0;
        Side winner = b.getWinner();
        if (winner != null) {
            if (winner.equals(p)) {
                return (b.size() * b.size() + 1);
            } else {
                return -1 * (b.size() * b.size() + 1);
            }
        }
        if (d == 0) {
            return staticEval(p, b);
        }
        for (int i = 0; i < b.size() * b.size(); i++) {
            if (b.isLegal(p, i)) {
                b.addSpot(p, i);
                int response = minmax(p.opposite(), b, d - 1,
                    -1 * x, moves);
                b.undo();
                if (-1 * response > x) {
                    bestMove = i;
                    x = -1 * response;
                    if (x >= cutoff) {
                        break;
                    }
                }
            }
        }
        moves.clear();
        moves.add(bestMove);
        return x;
    }

    /** Returns heuristic value of board B for player P.
     *  Higher is better for P. */
    private int staticEval(Side p, Board b) {
        return b.numOfSide(p);
    }

}
