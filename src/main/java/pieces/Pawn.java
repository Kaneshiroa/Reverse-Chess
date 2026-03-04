package pieces;

import datastructures.Vector2D;

public class Pawn implements Movement{

    private boolean isWhite;
    private Vector2D location;

    public Pawn(boolean isWhite, Vector2D location) {
        this.isWhite = isWhite;
        this.location = location;
    }
}
