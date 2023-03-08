package Simulation.Agent.AgentInterfaces;

import Simulation.Agent.AgentStructs.ColorModel;

import java.awt.*;
import java.util.Random;

public interface Attributes {

    void generateAttributes(Attributes attributesA, Attributes attributesB);

    int getRange();

    int getSize();

    int getLifespan();

    int getCreationAge();

    int getCreationSize();

    int getCreationDelay();

    int getEnergyCapacity();

    int getEnergyLostPerTile();

    int getEatAmount();

    int getCreationCost();

    String getName();

    Integer getID();

    Color getSeedColor();

    int getSpawningWeight();

    int getMutationChance();

    void setRange(int range);

    void setSize(int size);

    void setLifespan(int lifespan);

    void setCreationAge(int creationAge);

    void setCreationSize(int creationSize);

    void setCreationDelay(int creationDelay);

    void setName(String name);

    void setID(int code);

    void setSeedColor(Color seedColor);

    void setSpawningWeight(int spawningWeight);

    void setMutationChance(int mutationChance);

    Attributes copy();

    Attributes combine(Attributes attributes);

    void mutateAttributesColor(double a, double b, double c, int constant);

    void mutateSeedColor(int magnitude);

    Color getMutatingColor();

    Color getColor();

    void calculateAttributes();

    void setEnergyCapacity(int energyCapacity);

    void setEatAmount(int eatAmount);

    void setCreationCost(int creationCost);

    void setEnergyLostPerTile(int energyLostPerTile);

    void setMutatingColor(Color mutatingColor);

    ColorModel getColorModel();

    void setColorModel(ColorModel colorModel);

    int getRandomColorModelMagnitude();

    void setRandomColorModelMagnitude(int randomColorModelMagnitude);

    Random getRandom();
}
