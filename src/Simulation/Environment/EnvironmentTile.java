package Simulation.Environment;

import Simulation.Agent.AgentInterfaces.Agent;

import java.io.Serializable;

// This class represents the individual tiles that make up the world.
public class EnvironmentTile implements Serializable {

    private int isTerrain;

    private int energyLevel; // The energy level of this tile.

    private Location location; // The tiles location

    private Agent currentAgent; // The agent currently occupying this tile.

    public EnvironmentTile(int energyLevel, int x, int y) {
        this.energyLevel = energyLevel;
        this.location = new Location(x, y);
        this.currentAgent = null;
        isTerrain = 0;
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

    public boolean isTerrain() {
        if (isTerrain == 2) {
            return true;
        }
        return false;
    }

    public void setTerrain(int terrain) {
        isTerrain = terrain;
    }
}
