package Simulation.Agent.AgentInterfaces;

import Simulation.Agent.AgentStructs.AgentDecision;
import Simulation.Agent.AgentStructs.AgentVision;

public interface Motivation {

    AgentDecision run(AgentVision tile, Attributes attributes, Scores scores);

    Motivation copy();

    boolean equals(Motivation motivation);

    int getCode();

    int getBias();

    int getWeight();

    void setBias(int bias);

    void setWeight(int weight);

}
