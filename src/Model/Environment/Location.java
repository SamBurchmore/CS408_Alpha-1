package Model.Environment;

import java.io.Serializable;

public class Location implements Serializable {

    private int x = 0;
    private int y = 0;

    public Location(int x_, int y_) {
        this.x = x_;
        this.y = y_;
    }

    public boolean equals(Object compareSubject)
    {
        if(compareSubject instanceof Location) {
            Location other = (Location) compareSubject;
            return this.x == other.getX() && this.y == other.getY();
        }
        else {
            return false;
        }
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
