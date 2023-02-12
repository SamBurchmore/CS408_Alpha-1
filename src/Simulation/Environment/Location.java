package Simulation.Environment;

import java.io.Serializable;

public class Location implements Serializable {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
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
