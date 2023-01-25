package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentStructs.AgentType;

import java.awt.*;
import java.util.Random;

public abstract class BaseAttributes implements Attributes {

    private String name;
    private int code;
    private Color color;

    private int visionRange;
    private int movementRange;
    private int size;
    private int energyCapacity;
    private int eatAmount;
    private int lifespan;
    private int creationAge;
    private int creationAmount;
    private int creationDelay;

    public BaseAttributes(String name, int code, Color color, int visionRange, int movementRange, int size, int energyCapacity, int eatAmount, int lifespan, int creationAge, int creationAmount, int creationDelay) {
        this.code = code;
        this.name = name;
        this.color = color;
        this.visionRange = visionRange;
        this.movementRange = movementRange;
        this.size = size;
        this.energyCapacity = energyCapacity;
        this.eatAmount = eatAmount;
        this.lifespan = lifespan;
        this.creationAge = creationAge;
        this.creationAmount = creationAmount;
        this.creationDelay = creationDelay;
    }

    public BaseAttributes(Attributes attributesA, Attributes attributesB) {
        this.code = attributesA.getCode();
        this.name = attributesA.getName();
        this.color = attributesA.getColor();
        this.visionRange = attributesA.getVisionRange();
        this.movementRange = attributesA.getMovementRange();
        this.size = attributesA.getSize();
        this.energyCapacity = attributesA.getEnergyCapacity();
        this.eatAmount = attributesA.getEatAmount();
        this.lifespan = attributesA.getLifespan();
        this.creationAge = attributesA.getCreationAge();
        this.creationAmount = attributesA.getCreationAmount();
        this.creationDelay = attributesA.getCreationDelay();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    @Override
    public int getMovementRange() {
        return movementRange;
    }

    public void setMovementRange(int movementRange) {
        this.movementRange = movementRange;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getEnergyCapacity() {
        return energyCapacity;
    }

    public void setEnergyCapacity(int energyCapacity) {
        this.energyCapacity = energyCapacity;
    }

    @Override
    public int getEatAmount() {
        return eatAmount;
    }

    public void setEatAmount(int eatAmount) {
        this.eatAmount = eatAmount;
    }

    @Override
    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    @Override
    public int getCreationAge() {
        return creationAge;
    }

    public void setCreationAge(int creationAge) {
        this.creationAge = creationAge;
    }

    @Override
    public int getCreationAmount() {
        return creationAmount;
    }

    public void setCreationAmount(int creationAmount) {
        this.creationAmount = creationAmount;
    }

    @Override
    public int getCreationDelay() {
        return creationDelay;
    }

    public void setCreationDelay(int creationDelay) {
        this.creationDelay = creationDelay;
    }
}
