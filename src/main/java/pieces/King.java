package pieces;

import board.Board;
import datastructures.Vector2D;

import java.util.List;

public class King extends Piece implements Movement{

    public King(String color, Vector2D location) {
        super(location, color);
    }

    @Override
    public boolean isValidMove(Vector2D loc, Board board) {
        return possibleMoves(board).contains(loc);
    }

    @Override
    public List<Vector2D> possibleMoves(Board board) {
        return List.of();
    }

}
