package Model.Agents.AgentInterfaces;

import Model.Agents.AgentStructs.AgentType;

import java.awt.*;

public interface Attributes {

    int getRange();
    int getSize();
    int getEnergyCapacity();
    int getEatAmount();
    int getLifespan();
    int getCreationAge();
    int getCreationAmount();
    int getCreationDelay();
    String getName();
    int getCode();
    Color getColor();
    void setRange(int range);
    void setSize(int size);
    void setEnergyCapacity(int energyCapacity);
    void setEatAmount(int eatAmount);
    void setLifespan(int lifespan);
    void setCreationAge(int creationAge);
    void setCreationAmount(int creationAmount);
    void setCreationDelay(int creationDelay);
    void setName(String name);
    void setCode(int code);
    void setColor(Color color);
    double getSpawningWeight();
    void setSpawningWeight(double spawningWeight);

}
