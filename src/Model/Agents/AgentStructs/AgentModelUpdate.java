package Model.Agents.AgentStructs;

import Model.Agents.AgentInterfaces.Agent;
import Model.Environment.Location;

import java.util.ArrayList;

public class AgentModelUpdate {

    private Agent agent;
    private ArrayList<Agent> childAgents;

    private int eatAmount;
    public AgentModelUpdate(Agent agent, ArrayList<Agent> childAgents) {
        this.agent = agent;
        this.childAgents = childAgents;
        this.eatAmount = 0;
    }

    public AgentModelUpdate(Agent agent, ArrayList<Agent> childAgents, int eatAmount) {
        this.agent = agent;
        this.childAgents = childAgents;
        this.eatAmount = eatAmount;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public ArrayList<Agent> getChildAgents() {
        return childAgents;
    }

    public void setChildAgents(ArrayList<Agent> childAgents) {
        this.childAgents = childAgents;
    }

    public int getEatAmount() {
        return eatAmount;
    }

    public void setEatAmount(int eatAmount) {
        this.eatAmount = eatAmount;
    }
}
