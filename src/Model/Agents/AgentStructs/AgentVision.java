package Model.Agents.AgentStructs;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Environment.Location;

public class AgentVision {

    private int food_level;
    private Attributes agentAttributes;
    private Location location;
    private boolean isOccupied;
    private boolean inRange;


    public AgentVision(int food_level_, boolean isOccupied_ , Location location_, Attributes agentAttributes_) {
        this.food_level = food_level_;
        this.agentAttributes = agentAttributes_;
        this.location = location_;
        this.isOccupied = isOccupied_;
    }

    public AgentVision(int food_level_, boolean isOccupied_ , Location location_) {
        this.food_level = food_level_;
        this.agentAttributes = null;
        this.location = location_;
        this.isOccupied = isOccupied_;
    }


    public int getFoodLevel() {
        return this.food_level;
    }

    public boolean isOccupied() {
        return this.isOccupied;
    }

    public Attributes getAgentAttributes() {
        return this.agentAttributes;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isInRange() {
        return this.inRange;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }

}
