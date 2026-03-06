package pieces;

import board.Board;
import datastructures.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece implements Movement{

    public Rook(String color, Vector2D location) {
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
                {1,0}, //Right
                {-1,0}, //Left
                {0,1}, //Top
                {0,-1}, //Bottom
        };
        for (int[] pos : directions) {
            int x = getLoc().getX();
            int y = getLoc().getY();

            //We loop through the entire board for each path the rook has
            while (true) {
                x += pos[0];
                y += pos[1];

                Vector2D newPos = new Vector2D(x,y);
                if (!board.isInside(newPos)) {
                    break;
                }
                if (board.isEmpty(newPos)) {
                    moves.add(newPos);
                }
                else if (board.isEnemy(newPos, getColor())) {
                    moves.add(newPos);
                    break;
                }
                else {
                    break;
                }
            }
        }
        return moves;
    }

}
