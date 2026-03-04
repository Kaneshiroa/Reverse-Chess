package pieces;

import datastructures.Vector2D;

public class Knight implements Movement{

    private boolean isWhite;
    private Vector2D location;

    public Knight(boolean isWhite, Vector2D location) {
        this.isWhite = isWhite;
        this.location = location;
    }
}
