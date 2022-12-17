package Model.Environment;

import Model.Agents.AgentInterfaces.Agent;

// This class represents the individual tiles that make up the world.
public class EnvironmentTile {
    // The current food level of this tile.
    private int food_level;
    // The tiles location on the WorldGrid
    private Location location;
    // The agent currently occupying this tile.
    private Agent current_agent = null;

    public EnvironmentTile(int food_level, int x_, int y_) {
        this.food_level = food_level;
        this.location = new Location(x_, y_);
    }

    public Integer getFoodLevel() {
        return this.food_level;
    }

    public void setFoodLevel(Integer new_food_level) {
        this.food_level = new_food_level;
    }

    public boolean isOccupied() {
        return this.current_agent != null;
    }

    public void setOccupant(Agent new_agent) {
        this.current_agent = new_agent;
    }

    public Agent getOccupant() {
        return this.current_agent;
    }

    public Location getLocation() {
        return this.location;
    }

}
