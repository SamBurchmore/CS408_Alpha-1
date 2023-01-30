package Model.Agents.AgentStructs;

import Model.Environment.Location;

public class AgentDecision {

    private Location location;
    private AgentAction agentAction;
    private int decisionScore;

    public AgentDecision(Location location, AgentAction agentAction, int decisionScore) {
        this.location = location;
        this.agentAction = agentAction;
        this.decisionScore = decisionScore;
    }

    public Location getLocation() {
        return location;
    }

    public AgentAction getAgentAction() {
        return agentAction;
    }

    public int getDecisionScore() {
        return decisionScore;
    }
}
