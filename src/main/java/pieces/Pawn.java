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

        if (Objects.equals(getColor(), "Black")) {
            y -= 1;
            if (!this.getHasMoved()){
                Vector2D blkDoubleMove = new Vector2D(x, y-1);
                if (board.isEmpty(blkDoubleMove)){
                    moves.add(blkDoubleMove);
                }
            }
        }
        if (Objects.equals(getColor(), "White")) {
            y += 1;
            if (!this.getHasMoved()){
                Vector2D whtDoubleMove = new Vector2D(x, y+1);
                if (board.isEmpty(whtDoubleMove)){
                    moves.add(whtDoubleMove);
                }
            }
        }

        Vector2D leftCapture = new Vector2D(x - 1, y);
        Vector2D rightCapture = new Vector2D(x + 1, y);

        Vector2D newPos = new Vector2D(x,y);
        if (board.isInside(leftCapture) && board.isEnemy(leftCapture, getColor())) {
            moves.add(leftCapture);
        }
        if (board.isInside(rightCapture) && board.isEnemy(rightCapture, getColor())) {
            moves.add(rightCapture);
        }
        if (board.isInside(newPos) && (board.isEmpty(newPos)))  {
            moves.add(newPos);
        }
        return moves;
    }
}
