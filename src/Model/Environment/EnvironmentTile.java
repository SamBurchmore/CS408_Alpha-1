package Model.Environment;

import Model.Agents.AgentInterfaces.Agent;

import java.io.Serializable;

// This class represents the individual tiles that make up the world.
public class EnvironmentTile implements Serializable {
    // The current food level of this tile.
    private int energyLevel;
    // The tiles location on the WorldGrid
    private Location location;
    // The agent currently occupying this tile.
    private Agent currentAgent = null;

    public EnvironmentTile(int energyLevel, int x, int y) {
        this.energyLevel = energyLevel;
        this.location = new Location(x, y);
    }

    public Integer getEnergyLevel() {
        return this.energyLevel;
    }

    public void setFoodLevel(Integer energyLevel) {
        this.energyLevel = energyLevel;
    }

    public boolean isOccupied() {
        return this.currentAgent != null;
    }

    public void setOccupant(Agent newAgent) {
        this.currentAgent = newAgent;
    }

    public Agent getOccupant() {
        return this.currentAgent;
    }

    public Location getLocation() {
        return this.location;
    }

}
