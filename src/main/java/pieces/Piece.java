package pieces;

import datastructures.Vector2D;

public abstract class Piece {

    protected Vector2D loc;
    protected String color;
    private boolean hasMoved = false;

    public Piece(Vector2D loc, String color) {
        this.loc = loc;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public Vector2D getLoc() {
        return loc;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLoc(Vector2D loc) {
        this.loc = loc;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
