package Model.Agents.AgentInterfaces;

import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentVision;

import java.util.ArrayList;

public interface Reaction {

    AgentDecision react(ArrayList<AgentVision> agentVision, Attributes agentAttributes, Scores agentScores);

    Motivation getMotivations();

}
