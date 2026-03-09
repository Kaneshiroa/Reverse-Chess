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
        if (this.getHasMoved()){
            Vector2D rightSideCastle = new Vector2D(getLoc().getX() + 2, getLoc().getY());
            Vector2D leftSideCastle = new Vector2D(getLoc().getX() - 2, getLoc().getY());
            if (board.isEmpty(new Vector2D(getLoc().getX() + 1, getLoc().getY()))) {
                if(board.isEmpty(rightSideCastle)){
                    moves.add(rightSideCastle);
                }
            }
            if (board.isEmpty(new Vector2D(getLoc().getX() - 1, getLoc().getY()))) {
                if(board.isEmpty(leftSideCastle)){
                    moves.add(leftSideCastle);
                }
            }
        }
        return moves;
    }

}
