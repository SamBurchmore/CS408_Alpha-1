package Model.Agents.AgentInterfaces;

import Model.Agents.AgentStructs.AgentUpdate;
import Model.Agents.AgentStructs.AgentVision;

import java.util.ArrayList;

public interface Reaction {

    AgentUpdate react(ArrayList<AgentVision> agentVision, Attributes agentAttributes, Scores agentScores);

    Motivations getAgentMotivations();

}
