package Model.Agents.AgentInterfaces;

import Model.Agents.AgentStructs.AgentType;

public interface Attributes {

    void mutate();
    int mutationMagnitude();
    int getSpeed();
    int getSize();
    int getVision();
    int getEatAmount();
    int getAgentCode();

}
