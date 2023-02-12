package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentInterfaces.Attributes;

import java.awt.*;
import java.io.Serializable;

public class BasicAttributes implements Attributes, Serializable {

    private double spawningWeight;
    private String name;
    private int code;
    private Color color;
    private boolean mutates;
    private int mutationMagnitude;

    private int creationDelay; // constant, can be set by user

    private int range; // Mutates
    private int size; // Mutates
    private int creationAge; // Mutates
    private int creationAmount; // Mutates

    private int energyLostPerTurn; // = size^0.75
    private int lifespan; // = 20 + size^2
    private int eatAmount; // = size^0.75
    private int energyCapacity; // = 50 + size^1.75
    private int creationCost; // - (energyCapacity / 2) / creationAmount

    public BasicAttributes(double spawningWeight, String name, int code, Color color, boolean mutates, int mutationMagnitude, int range, int size, int creationAge, int creationAmount, int creationDelay) {
        this.spawningWeight = spawningWeight;
        this.code = code;
        this.name = name;
        this.color = color;
        this.mutates = mutates;
        this.mutationMagnitude = mutationMagnitude;

        this.range = range;
        this.size = size;
        this.creationAge = creationAge;
        this.creationAmount = creationAmount;

        // Calculate attributes that are based on other attributes
        this.lifespan = 20 + this.size * this.size;
        this.energyLostPerTurn = (int) Math.round(Math.pow(size, 0.75));
        this.eatAmount = (int) Math.round(Math.pow(size, 0.75)) * 2;
        this.energyCapacity = 50 + (int) Math.round(Math.pow(size, 1.75));
        this.creationCost = (this.energyCapacity / 2) / creationAmount;


        // Constants
        this.creationDelay = creationDelay;

    }

    public BasicAttributes(Attributes attributesA, Attributes attributesB) {
        this.code =  attributesA.getCode();
        this.name = attributesA.getName();
        this.color = attributesA.getColor();
        this.range = attributesA.getRange();
        this.size = attributesA.getSize();
        this.creationAge = attributesA.getCreationAge();
        this.creationAmount = attributesA.getCreationAmount();
        this.creationDelay = attributesA.getCreationDelay();
        this.mutates = attributesA.getMutates();
        this.mutationMagnitude = attributesA.getMutationMagnitude();

        // Calculate attributes that are based on other attributes
        this.lifespan = 50 + this.size * this.size;
        this.energyLostPerTurn = (int) Math.round(Math.pow(size, 0.75));
        this.eatAmount = this.size * 2;
        this.energyCapacity = 50 + (int) Math.round(Math.pow(size, 1.75));
        this.creationCost = (this.energyCapacity / 2) / creationAmount;

        this.spawningWeight = attributesA.getSpawningWeight();

    }

    public BasicAttributes() {

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

    @Override
    public void setRange(int range) {
        this.range = range;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setEnergyCapacity(int energyCapacity) {
        this.energyCapacity = energyCapacity;
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

    @Override
    public int getEnergyCapacity() {
        return energyCapacity;
    }

    @Override
    public int getEnergyLostPerTurn() {
        return energyLostPerTurn;
    }

    @Override
    public int getEatAmount() {
        return eatAmount;
    }

    @Override
    public int getCreationCost() {
        return creationCost;
    }

    public void setCreationDelay(int creationDelay) {
        this.creationDelay = creationDelay;
    }

    @Override
    public double getSpawningWeight() {
        return spawningWeight;
    }

    @Override
    public void setSpawningWeight(double spawningWeight) {
        this.spawningWeight = spawningWeight;
    }

    @Override
    public String toString() {
        return "BaseAttributes{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", color=" + color +
                ", range=" + range +
                ", size=" + size +
                ", energyCapacity=" + energyCapacity +
                ", lifespan=" + lifespan +
                ", creationAge=" + creationAge +
                ", creationAmount=" + creationAmount +
                ", creationDelay=" + creationDelay +
                '}';
    }

    @Override
    public boolean getMutates() {
        return mutates;
    }

    @Override
    public void setMutates(boolean mutates) {
        this.mutates = mutates;
    }

    @Override
    public int getMutationMagnitude() {
        return mutationMagnitude;
    }

    @Override
    public void setMutationMagnitude(int mutationMagnitude) {
        this.mutationMagnitude = mutationMagnitude;
    }

    @Override
    public Attributes copy() {
        return new BasicAttributes(
                getSpawningWeight(),
                getName(),
                getCode(),
                getColor(),
                getMutates(),
                getMutationMagnitude(),
                getRange(),
                getSize(),
                getCreationAge(),
                getCreationAmount(),
                getCreationDelay());
    }


}
