package pieces;

import datastructures.Vector2D;

public class King implements Movement{

    private boolean isWhite;
    private Vector2D location;

    public King(boolean isWhite, Vector2D location) {
        this.isWhite = isWhite;
        this.location = location;
    }
}
