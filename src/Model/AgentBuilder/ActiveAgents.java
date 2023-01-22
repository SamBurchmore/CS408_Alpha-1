package Model.AgentBuilder;

import Model.Agents.AgentBaseComponents.BaseAgent;
import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.Agent;
import Model.Agents.AgentStructs.AgentType;
import Model.Environment.Location;

import java.awt.*;
import java.util.ArrayList;

public class ActiveAgents {

    private final int _agentNumber_ = 8;

    ArrayList<Agent> activeAgents;

    public ActiveAgents() {
        activeAgents = new ArrayList<>();
    }

    public Agent getAgent(int index) {
        return activeAgents.get(index);
    }

    public void setAgent(Agent agent, int index) {
        activeAgents.set(index, agent);
    }

    public void addAgent(Agent agent, int index) {
        activeAgents.add(agent);
    }

    public ArrayList<Agent> getActiveAgents() {
        return activeAgents;
    }
}
