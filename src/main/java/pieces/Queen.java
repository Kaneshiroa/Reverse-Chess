package pieces;

import board.Board;
import datastructures.Vector2D;

public class Queen extends Piece implements Movement{

    private String color;
    private Vector2D location;

    public Queen(String color, Vector2D location) {
        super(location, color);
    }

    @Override
    public boolean isValidMove(Vector2D loc, Board board) {
        return loc.getX() <= 8 && loc.getX() >= 0 && loc.getY() <= 8 && loc.getY() >= 0;
    }
}
