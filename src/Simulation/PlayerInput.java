package Simulation;

import Simulation.Environment.Location;

public class PlayerInput {

    private Location clickLocation;
    private int clickType;

    public PlayerInput(Location clickLocation, int clickType) {
        this.clickLocation = clickLocation;
        this.clickType = clickType;
    }

    public Location getClickLocation() {
        return clickLocation;
    }

    public void setClickLocation(Location clickLocation) {
        this.clickLocation = clickLocation;
    }

    public int getClickType() {
        return clickType;
    }

    public void setClickType(int clickType) {
        this.clickType = clickType;
    }
}
