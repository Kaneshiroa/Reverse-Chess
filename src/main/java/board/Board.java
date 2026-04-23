package board;

import datastructures.Vector2D;
import pieces.*;

public class Board {

    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        setupStandardBoard();
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
        if (piece != null) {
            piece.setLoc(loc);
        }
    }

    public void Move(Vector2D start, Vector2D end) {
        Piece piece = getPiece(start);
        if (piece != null) {
            //Prevents castling after movement between king and rook, and moving two spaces as a pawn if moved once
            piece.setHasMoved(true);
            //Move the piece to the new square
            setPiece(end,piece);
            //Then reset the tile they just moved from
            setPiece(start,null);
        }
    }

    //Check if a piece is inside specified tile
    public boolean isInside(Vector2D loc) {
        return loc.getX() >= 0 && loc.getX() < 8 && loc.getY() >= 0 && loc.getY() < 8;
    }

    //Method to check if a tile is empty
    public boolean isEmpty(Vector2D loc) {
        return getPiece(loc) == null;
    }

    public boolean isEnemy(Vector2D loc, String color) {
        //Get our piece
        Piece piece = getPiece(loc);
        //Check its color, if it isn't what we enter as the parameter, that means its an enemy
        return piece != null && !piece.getColor().equals(color);
    }

    public boolean isSquareAttacked(Vector2D target, String myColor) {
        String enemyColor;
        if (myColor.equals("White")) {
            enemyColor = "Black";
        } else {
            enemyColor = "White";
        }
        //Sliding Attackers (Rook, Bishop, Queen)
        //Horizontal/Vertical (Looking for Rook or Queen)
        int[][] rookDirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        if (checkSliding(target, enemyColor, rookDirs, true)) {
            return true;
        }
        //Diagonals (Looking for Bishop or Queen)
        int[][] bishopDirs = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        if (checkSliding(target, enemyColor, bishopDirs, false)) {
            return true;
        }
        //Knights
        int[][] knightMoves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        for (int[] m : knightMoves) {
            Vector2D p = new Vector2D(target.getX() + m[0], target.getY() + m[1]);
            if (isInside(p)) {
                Piece piece = getPiece(p);
                if (piece != null) {
                    if (piece instanceof Knight && piece.getColor().equals(enemyColor)) {
                        return true;
                    }
                }
            }
        }
        //Pawns
        int pawnDirection;
        if (myColor.equals("White")) {
            pawnDirection = 1; // Look up for Black pawns
        } else {
            pawnDirection = -1; // Look down for White pawns
        }

        int[][] pawnCapture = {{1, pawnDirection}, {-1, pawnDirection}};
        for (int[] c : pawnCapture) {
            Vector2D p = new Vector2D(target.getX() + c[0], target.getY() + c[1]);
            if (isInside(p)) {
                Piece piece = getPiece(p);
                if (piece != null) {
                    if (piece instanceof Pawn && piece.getColor().equals(enemyColor)) {
                        return true;
                    }
                }
            }
        }
        //Kings
        int[][] kingDirs = {{1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        for (int[] k : kingDirs) {
            Vector2D p = new Vector2D(target.getX() + k[0], target.getY() + k[1]);
            if (isInside(p)) {
                Piece piece = getPiece(p);
                if (piece != null) {
                    if (piece instanceof King && piece.getColor().equals(enemyColor)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Helper function for isSquareAttacked
    private boolean checkSliding(Vector2D target, String enemyColor, int[][] dirs, boolean isRookStyle) {
        for (int[] d : dirs) {
            int x = target.getX() + d[0];
            int y = target.getY() + d[1];

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                Piece p = board[x][y];
                if (p != null) {
                    if (p.getColor().equals(enemyColor)) {
                        if (p instanceof Queen) {
                            return true;
                        }
                        if (isRookStyle && p instanceof Rook) {
                            return true;
                        }
                        if (!isRookStyle && p instanceof Bishop) {
                            return true;
                        }
                    }
                    break; //Path blocked by any piece
                }
                x += d[0];
                y += d[1];
            }
        }
        return false;
    }

    public void setupStandardBoard() {
        // 1. White Back Rank (Row 0)
        setPiece(new Vector2D(0, 0), new Rook("White", new Vector2D(0, 0)));
        setPiece(new Vector2D(1, 0), new Knight("White", new Vector2D(1, 0)));
        setPiece(new Vector2D(2, 0), new Bishop("White", new Vector2D(2, 0)));
        setPiece(new Vector2D(3, 0), new Queen("White", new Vector2D(3, 0)));
        setPiece(new Vector2D(4, 0), new King("White", new Vector2D(4, 0)));
        setPiece(new Vector2D(5, 0), new Bishop("White", new Vector2D(5, 0)));
        setPiece(new Vector2D(6, 0), new Knight("White", new Vector2D(6, 0)));
        setPiece(new Vector2D(7, 0), new Rook("White", new Vector2D(7, 0)));

        // 2. White Pawns (Row 1)
        for (int i = 0; i < 8; i++) {
            setPiece(new Vector2D(i, 1), new Pawn("White", new Vector2D(i, 1)));
        }

        // 3. Black Back Rank (Row 7)
        setPiece(new Vector2D(0, 7), new Rook("Black", new Vector2D(0, 7)));
        setPiece(new Vector2D(1, 7), new Knight("Black", new Vector2D(1, 7)));
        setPiece(new Vector2D(2, 7), new Bishop("Black", new Vector2D(2, 7)));
        setPiece(new Vector2D(3, 7), new Queen("Black", new Vector2D(3, 7)));
        setPiece(new Vector2D(4, 7), new King("Black", new Vector2D(4, 7)));
        setPiece(new Vector2D(5, 7), new Bishop("Black", new Vector2D(5, 7)));
        setPiece(new Vector2D(6, 7), new Knight("Black", new Vector2D(6, 7)));
        setPiece(new Vector2D(7, 7), new Rook("Black", new Vector2D(7, 7)));

        // 4. Black Pawns (Row 6)
        for (int i = 0; i < 8; i++) {
            setPiece(new Vector2D(i, 6), new Pawn("Black", new Vector2D(i, 6)));
        }
    }

    public void executeCastle(Vector2D kingStart, Vector2D kingEnd) {
        //Move the King normally
        Move(kingStart, kingEnd);

        //Move the Rook manually based on which side was clicked
        if (kingEnd.getX() == 6) { //Kingside (to the right)
            Move(new Vector2D(7, kingStart.getY()), new Vector2D(5, kingStart.getY()));
        } else if (kingEnd.getX() == 2) { //Queenside (to the left)
            Move(new Vector2D(0, kingStart.getY()), new Vector2D(3, kingStart.getY()));
        }
    }
}
