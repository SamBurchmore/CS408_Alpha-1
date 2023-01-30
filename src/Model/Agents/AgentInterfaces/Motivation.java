package Model.Agents.AgentInterfaces;

import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentVision;

public interface Motivation {

    AgentDecision run(AgentVision tile, Attributes attributes, Scores scores);

    Motivation copy();

}
