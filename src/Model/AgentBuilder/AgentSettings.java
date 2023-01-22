package Model.AgentBuilder;

import java.awt.*;

public class AgentSettings {

    private int lifespan;
    private int energyCapacity;
    private int energyAmount;
    private int visionRange;
    private int movementRange;
    private Color color;

    public AgentSettings(int lifespan, int energyCapacity, int eatAmount, int visionRange, int movementRange, Color color) {
        this.lifespan = lifespan;
        this.energyCapacity = energyCapacity;
        this.energyAmount = eatAmount;
        this.visionRange = visionRange;
        this.movementRange = movementRange;
        this.color = color;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public int getEnergyCapacity() {
        return energyCapacity;
    }

    public void setEnergyCapacity(int energyCapacity) {
        this.energyCapacity = energyCapacity;
    }

    public int getEnergyAmount() {
        return energyAmount;
    }

    public void setEnergyAmount(int energyAmount) {
        this.energyAmount = energyAmount;
    }

    public int getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    public int getMovementRange() {
        return movementRange;
    }

    public void setMovementRange(int movementRange) {
        this.movementRange = movementRange;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
