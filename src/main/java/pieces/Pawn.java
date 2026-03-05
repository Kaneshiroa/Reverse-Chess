package pieces;

import board.Board;
import datastructures.Vector2D;

import java.util.List;

public class Pawn extends Piece implements Movement{

    private String color;
    private Vector2D location;

    public Pawn(String color, Vector2D location) {
        super(location, color);
    }

    @Override
    public boolean isValidMove(Vector2D loc, Board board) {
        return loc.getX() <= 8 && loc.getX() >= 0 && loc.getY() <= 8 && loc.getY() >= 0;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //TODO: Get logic working for isValidMove to work with the conditional to set a location
    public void setLocation(Vector2D location) {
        if (location.getX() < 0 || location.getY() < 0 || location.getY() >= 8 || location.getX() >= 8) {
            throw new IllegalArgumentException("Cannot place piece outside board");
        }
        this.location = location;
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
