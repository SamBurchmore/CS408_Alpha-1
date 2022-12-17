package Model.Agents.AgentStructs;

import Model.Environment.Location;

public class AgentDecision {

    private Location location = null;
    // Set the default action to NONE.
    private AgentAction agentAction = AgentAction.NONE;

    private int rank = 0;

    public void setLocation(Location location) {
        this.location = location;
        // If a none null location is set, and the current action is NONE, then change the action to MOVE.
        if (this.location != null && this.agentAction == AgentAction.NONE) {
            this.agentAction = AgentAction.MOVE;
        }
    }

    public void setAgentAction(AgentAction agentAction) {
        this.agentAction = agentAction;
    }

    public AgentAction getAgentAction() {
        return this.agentAction;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isNull() {
        return this.location == null && this.agentAction == null;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
