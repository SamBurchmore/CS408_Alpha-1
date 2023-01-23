package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Attributes;

import java.awt.*;

public abstract class BaseAttributes implements Attributes {

    private int speed;
    private int size;
    private int vision;
    private int eatAmount;
    private int creationDelay;
    private int maxEnergy;
    private int maxAge;
    private int agentCode;
    private Color agentColour;

    public BaseAttributes(int speed, int size, int vision, int eatAmount, int creationDelay, int maxEnergy, int maxAge, int agentCode, Color agentColour) {
        this.speed = speed;
        this.size = size;
        this.vision = vision;
        this.eatAmount = eatAmount;
        this.creationDelay = creationDelay;
        this.maxEnergy = maxEnergy;
        this.maxAge = maxAge;
        this.agentCode = agentCode;
        this.agentColour = agentColour;
    }

    public BaseAttributes(Attributes attributesA, Attributes attributesB){
        this.speed = attributesA.getSpeed();
        this.size = attributesA.getSize();
        this.vision = attributesA.getVision();
        this.eatAmount = attributesA.getEatAmount();
        this.creationDelay = attributesA.getCreationDelay();
        this.maxEnergy = attributesA.getMaxEnergy();
        this.maxAge = attributesA.getMaxAge();
        this.agentCode = attributesA.getAgentCode();
        this.agentColour = attributesA.getAgentColour();
    }

    @Override
    public String toString() {
        return "Movement Range: " + speed + ". Size: " + size + ". Vision Range: " + vision + ". Eat Amount: " + eatAmount + ". Creation Delay: " + creationDelay + ". Max Energy: " + maxEnergy + ". Max Age: " + maxAge + ".";
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getVision() {
        return vision;
    }

    @Override
    public int getEatAmount() {
        return eatAmount;
    }

    @Override
    public int getCreationDelay() {
        return creationDelay;
    }

    @Override
    public int getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public int getAgentCode() {
        return agentCode;
    }

    @Override
    public Color getAgentColour() {
        return agentColour;
    }
}

