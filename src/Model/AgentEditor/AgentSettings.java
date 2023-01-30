package Model.AgentEditor;

import Model.Agents.AgentConcreteComponents.BasicAttributes;
import Model.Agents.AgentInterfaces.Attributes;

import java.awt.*;

public class AgentSettings {

    private Attributes attributes;

    public AgentSettings(int spawningWeight, String name, int code, Color color, int visionRange, int movementRange, int size, int energyCapacity, int eatAmount, int lifespan, int creationAge, int creationAmount, int creationDelay) {
        this.attributes = new BasicAttributes(spawningWeight, name, code, color, visionRange, movementRange, size, energyCapacity, eatAmount, lifespan, creationAge, creationAmount, creationDelay);
    }

    public AgentSettings(Attributes attributes) {
        this.attributes = attributes;
    }

    public Attributes getAgentAttributes() {
        return attributes;
    }

    public void setAgentAttributes(Attributes agentAttributes) {
        this.attributes = agentAttributes;
    }

    public String getName() {
        return attributes.getName();
    }

    public void setName(String name) {
        attributes.setName(name);
    }

    public Color getColor() {
        return attributes.getColor();
    }

    public void setColor(Color color) {
        attributes.setColor(color);
    }

    public int getEnergyCapacity() {
        return attributes.getEnergyCapacity();
    }

    public void setEnergyCapacity(int energyCapacity) {
        attributes.setEnergyCapacity(energyCapacity);
    }

    public int getEatAmount() {
        return attributes.getEatAmount();
    }

    public void setEatAmount(int eatAmount) {
        attributes.setEatAmount(eatAmount);
    }

    public int getLifespan() {
        return attributes.getLifespan();
    }

    public void setLifespan(int lifespan) {
        attributes.setLifespan(lifespan);
    }

    public int getVisionRange() {
        return attributes.getVisionRange();
    }

    public void setVisionRange(int visionRange) {
        attributes.setVisionRange(visionRange);
    }

    public int getMovementRange() {
        return attributes.getMovementRange();
    }

    public void setMovementRange(int movementRange) {
        attributes.setMovementRange(movementRange);
    }

    public int getSize() {
        return attributes.getSize();
    }

    public void setSize(int size) {
        attributes.setSize(size);
    }

    public int getCreationAge() {
        return attributes.getCreationAge();
    }

    public void setCreationAge(int creationAge) {
        attributes.setCreationAge(creationAge);
    }

    public int getCreationAmount() {
        return attributes.getCreationAmount();
    }

    public void setCreationAmount(int creationAmount) {
        attributes.setCreationAmount(creationAmount);
    }

    public int getCreationDelay() {
        return attributes.getCreationDelay();
    }

    public void setCreationDelay(int creationDelay) {
        attributes.setCreationDelay(creationDelay);
    }

    public void setCode(int code) {
        attributes.setCode(code);
    }

    public int getCode() {
        return attributes.getCode();
    }

    public int getSpawningWeight() {
        return attributes.getSpawningWeight();
    }

    public void setSpawningWeight(int spawningWeight) {
        attributes.setSpawningWeight(spawningWeight);
    }
}
