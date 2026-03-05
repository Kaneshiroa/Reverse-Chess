package board;

import datastructures.Vector2D;
import pieces.Piece;

public class Board {

    private Piece[][] board;

    /*
    [R][K][B][Q][K][B][K][R]
    [P][P][P][P][P][P][P][P]
    [x][o][x][o][x][o][x][o]
    [o][x][o][x][o][x][o][x]
    [x][o][x][o][x][o][x][o]
    [o][x][o][x][o][x][o][x]
    [P][P][P][P][P][P][P][P]
    [R][K][B][Q][K][B][K][R]
     */

    public Board() {
        board = new Piece[8][8];
    }

    public Piece getPiece(Vector2D loc) {
        //Gets the piece at said vector2D
        return board[loc.getX()][loc.getY()];
    }

    public void setPiece(Vector2D loc, Piece piece) {
        //Check if in-bound of the board before setting the location of the new piece
        if (loc.getX() < 0 || loc.getY() < 0 || loc.getY() >= 8 || loc.getX() >= 8) {
            throw new IllegalArgumentException("Cannot place piece outside board");
        }
        board[loc.getX()][loc.getY()] = piece;
        piece.setLoc(loc);
    }

    public boolean isInside(Vector2D loc) {
        return loc.getX() >= 0 && loc.getX() < 8 && loc.getY() >= 0 && loc.getY() < 8;
    }
}
