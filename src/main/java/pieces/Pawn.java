package pieces;

import board.Board;
import datastructures.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pawn extends Piece implements Movement{

    public Pawn(String color, Vector2D location) {
        super(location, color);
    }

    @Override
    public boolean isValidMove(Vector2D loc, Board board) {
        return possibleMoves(board).contains(loc);
    }
    //    private static boolean isPromoRank(Side side, Move move) {
    //        if (side.equals(Side.WHITE) &&
    //                move.getTo().getRank().equals(Rank.RANK_8)) {
    //            return true;
    //        } else return side.equals(Side.BLACK) &&
    //                move.getTo().getRank().equals(Rank.RANK_1);
    //
    //    }

    @Override
    public List<Vector2D> possibleMoves(Board board) {
        List<Vector2D> moves = new ArrayList<>();
        int x = getLoc().getX();
        int y = getLoc().getY();

        int direction;
        if (getColor().equals("White")) {
            direction = 1;
        } else {
            direction = -1;
        }

        // One Square forward
        Vector2D oneStep = new Vector2D(x, y + direction);
        if (board.isInside(oneStep) && board.isEmpty(oneStep)) {
            moves.add(oneStep);
            // Two squares forward
            if (!this.getHasMoved()) {
                Vector2D twoStep = new Vector2D(x, y + (2 * direction));
                if (board.isInside(twoStep) && board.isEmpty(twoStep)) {
                    moves.add(twoStep);
                }
            }
        }

        // Captures
        int[] captureCols = {x - 1, x + 1};
        for (int nextX : captureCols) {
            Vector2D diagPos = new Vector2D(nextX, y + direction);
            if (board.isInside(diagPos) && board.isEnemy(diagPos, getColor())) {
                moves.add(diagPos);
            }
        }

        return moves;
    }
}
