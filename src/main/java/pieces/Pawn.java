package pieces;

import board.Board;
import datastructures.Vector2D;

import java.util.List;

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
        return List.of();
    }
}
