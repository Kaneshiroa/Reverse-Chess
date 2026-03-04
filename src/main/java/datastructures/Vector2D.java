package datastructures;

import java.util.Objects;

public class Vector2D {

    private int x;
    private int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        //Check if it is the same object in memory
        if (this == obj) {
            return true;
        }
        //If it's an invalid object or the class isnt same, ret false
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Vector2D other = (Vector2D) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        //Identifier for Vector2D obj
        return Objects.hash(x, y);
    }
}
