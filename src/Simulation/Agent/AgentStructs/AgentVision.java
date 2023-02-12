package Simulation.Agent.AgentStructs;

import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Scores;
import Simulation.Environment.Location;

public class AgentVision {

    private final int foodLevel;
    private final Attributes occupantAttributes;
    private final Scores occupantScores;
    private final Location location;
    private final boolean isOccupied;

    public AgentVision(int foodLevel, boolean isOccupied , Location location, Attributes occupantAttributes, Scores occupantScores) {
        this.foodLevel = foodLevel;
        this.occupantAttributes = occupantAttributes;
        this.occupantScores = occupantScores;
        this.location = location;
        this.isOccupied = isOccupied;
    }

    public AgentVision(int foodLevel, boolean isOccupied , Location location) {
        this.foodLevel = foodLevel;
        this.occupantAttributes = null;
        this.occupantScores = null;
        this.location = location;
        this.isOccupied = isOccupied;
    }


    public int getFoodLevel() {
        return this.foodLevel;
    }

    public boolean isOccupied() {
        return this.isOccupied;
    }

    public Attributes getOccupantAttributes() {
        return this.occupantAttributes;
    }

    public Scores getOccupantScores() { return this.occupantScores; }

    public Location getLocation() {
        return this.location;
    }



}
