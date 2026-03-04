package pieces;

import datastructures.Vector2D;

public class Rook implements Movement{

    private boolean isWhite;
    private Vector2D location;

    public Rook(boolean isWhite, Vector2D location) {
        this.isWhite = isWhite;
        this.location = location;
    }
}
