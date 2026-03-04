package pieces;

import datastructures.Vector2D;

public class Bishop implements Movement{

    private boolean isWhite;
    private Vector2D location;

    public Bishop(boolean isWhite, Vector2D location) {
        this.isWhite = isWhite;
        this.location = location;
    }
}
