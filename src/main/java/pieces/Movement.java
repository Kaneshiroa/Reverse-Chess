package pieces;

import board.Board;
import datastructures.Vector2D;

import java.util.List;

public interface Movement {


    /* Define different movements for each piece type
       King moves x1 in diag/horizontal/vertical
       Queen moves all possible squares diag/horizontal/vertical
       Bishop moves all possible diag squares
       Rook moves all vertical/horizontal squares
       Knight moves all L shaped squares, ex. 2 up 1 right, 2 up 1 left, 2 right 1 down, 2 left 1 down
       Pawn moves x1 forward besides start, remember en passant, captures top right or top left
     */

    boolean isValidMove(Vector2D loc, Board board);

    List<Vector2D> possibleMoves(Board board);



}
