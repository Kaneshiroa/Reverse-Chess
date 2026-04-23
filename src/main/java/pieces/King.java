package pieces;

import board.Board;
import datastructures.Vector2D;

import java.util.ArrayList;
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
        List<Vector2D> moves = new ArrayList<>();
        int[][] directions = {
                {0,1}, //Goes up 1
                {1,0}, //Goes right 1
                {-1,0}, //Goes left 1
                {0,-1}, //Goes down 1
                {-1,-1}, //Goes bottom left
                {-1,1}, //Goes top left
                {1,1}, //Goes top right
                {1,-1} //Goes bottom right
        };
        for (int[] pos : directions) {
            Vector2D newPos = new Vector2D(getLoc().getX() + pos[0], getLoc().getY() + pos[1]);
            //We would check to see if the new location is within the chessboard, and is empty or has an enemy
            if (board.isInside(newPos) && (board.isEmpty(newPos) || board.isEnemy(newPos, getColor())))  {
                //We would add it to our list of possible moves if true
                moves.add(newPos);
            }
        }
        //checking for castling moves
        if (!this.getHasMoved()) {
            int y = getLoc().getY();

            //Right Castle
            Vector2D r1 = new Vector2D(5, y); // Pass-through
            Vector2D r2 = new Vector2D(6, y); // Landing
            Piece rightRook = board.getPiece(new Vector2D(7, y));

            if (board.isEmpty(r1) && board.isEmpty(r2) && rightRook instanceof Rook) {
                if (!rightRook.getHasMoved()) {
                    //Check King doesn't move through check
                    if (!board.isSquareAttacked(getLoc(), getColor()) &&
                            !board.isSquareAttacked(r1, getColor()) &&
                            !board.isSquareAttacked(r2, getColor())) {
                        moves.add(r2);
                    }
                }
            }
            //Left Castle
            Vector2D l1 = new Vector2D(3, y); // Pass-through
            Vector2D l2 = new Vector2D(2, y); // Landing
            Vector2D l3 = new Vector2D(1, y); // B-file (must be empty)
            Piece leftRook = board.getPiece(new Vector2D(0, y));

            if (board.isEmpty(l1) && board.isEmpty(l2) && board.isEmpty(l3) && leftRook instanceof Rook) {
                if (!leftRook.getHasMoved()) {
                    if (!board.isSquareAttacked(getLoc(), getColor()) &&
                            !board.isSquareAttacked(l1, getColor()) &&
                            !board.isSquareAttacked(l2, getColor())) {
                        moves.add(l2);
                    }
                }
            }
        }
        return moves;
    }

}
