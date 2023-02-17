package Simulation.Agent.AgentInterfaces;

import java.awt.*;

public interface Attributes {

    int getRange();
    int getSize();
    int getLifespan();
    int getCreationAge();
    int getCreationSize();
    int getCreationDelay();
    int getEnergyCapacity();
    int getEnergyLostPerTurn();
    int getEatAmount();
    int getCreationCost();
    String getName();
    int getCode();
    Color getSeedColor();
    double getSpawningWeight();
    int getMutationMagnitude();
    void setRange(int range);
    void setSize(int size);
    void setLifespan(int lifespan);
    void setCreationAge(int creationAge);
    void setCreationSize(int creationSize);
    void setCreationDelay(int creationDelay);
    void setName(String name);
    void setCode(int code);
    void setSeedColor(Color seedColor);
    void setSpawningWeight(double spawningWeight);
    void setMutationMagnitude(int mutationMagnitude);
    Attributes copy();
    void generateColor(double a, double b, double c, int constant);
    Color getMutatingColor();
}
