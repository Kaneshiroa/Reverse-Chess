package pieces;

import datastructures.Vector2D;

public class Queen implements Movement{

    private boolean isWhite;
    private Vector2D location;

    public Queen(boolean isWhite, Vector2D location) {
        this.isWhite = isWhite;
        this.location = location;
    }
}
