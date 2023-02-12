package Simulation.Agent.AgentInterfaces;

import java.awt.*;

public interface Attributes {

    int getRange();
    int getSize();
    int getLifespan();
    int getCreationAge();
    int getCreationAmount();
    int getCreationDelay();
    int getEnergyCapacity();
    int getEnergyLostPerTurn();
    int getEatAmount();
    int getCreationCost();
    String getName();
    int getCode();
    Color getColor();
    double getSpawningWeight();
    boolean getMutates();
    int getMutationMagnitude();
    void setRange(int range);
    void setSize(int size);
    void setLifespan(int lifespan);
    void setCreationAge(int creationAge);
    void setCreationAmount(int creationAmount);
    void setCreationDelay(int creationDelay);
    void setName(String name);
    void setCode(int code);
    void setColor(Color color);
    void setSpawningWeight(double spawningWeight);
    void setMutates(boolean mutates);
    void setMutationMagnitude(int mutationMagnitude);
    Attributes copy();

}
