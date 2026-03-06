package pieces;

import board.Board;
import datastructures.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece implements Movement{

    public Knight(String color, Vector2D location) {
        super(location, color);
    }

    @Override
    public boolean isValidMove(Vector2D loc, Board board) {
        return possibleMoves(board).contains(loc);
    }

    @Override
    public List<Vector2D> possibleMoves(Board board) {
        List<Vector2D> moves = new ArrayList<>();
        int[][] directions = {
                {1,2}, //Top Right L
                {-1,2}, //Top Left L
                {-2,1}, //Left Up L
                {-2,-1}, //Left Down L
                {-1,-2}, //Down Left L
                {1,-2}, //Down Right L
                {2,1}, //Right Top L
                {2,-1} //Right Bottom L
        };
        for (int[] pos : directions) {
            Vector2D newPos = new Vector2D(getLoc().getX() + pos[0], getLoc().getY() + pos[1]);
            if (board.isInside(newPos) && (board.isEmpty(newPos) || board.isEnemy(newPos, getColor()))) {
                moves.add(newPos);
            }
        }
        return moves;
    }
}
