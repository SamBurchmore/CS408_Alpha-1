package Simulation.AgentUtility;

import Simulation.Agent.AgentInterfaces.Agent;

import java.util.ArrayList;

public class ActiveAgents {

    final ArrayList<Agent> activeAgents;

    public ActiveAgents() {
        activeAgents = new ArrayList<>();
    }

    public Agent getAgent(int index) {
        return activeAgents.get(index);
    }

    public void addAgent(Agent agent, int index) {
        activeAgents.add(index, agent);
    }

    public ArrayList<Agent> getActiveAgents() {
        return activeAgents;
    }
}
