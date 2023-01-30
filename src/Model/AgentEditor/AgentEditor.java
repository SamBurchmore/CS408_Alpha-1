package Model.AgentEditor;

import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.Agent;
import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivation;
import Model.Agents.AgentStructs.AgentType;
import Model.Environment.Location;

import java.awt.*;
import java.util.ArrayList;

public class AgentEditor {

    private final int AGENT_NUMBER = 8;

    private ActiveAgents activeAgents;

    private int editingAgentIndex;

    public AgentEditor() {
        activeAgents = new ActiveAgents();
//        for (int i = 0; i < AGENT_NUMBER; i++) { //                                                                                                                                         (String name, int code, Color color, int visionRange, int movementRange, int size, int energyCapacity, int eatAmount, int lifespan, int creationAge, int creationAmount, int creationDelay) {
//
//            activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new BasicAttributes(1, "Agent " + (i+1), i, new Color(245 - i * (i * 5), 0, i * (i * 5)), 1, 1, 1, 16, 2, 32, 5, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), i);
//        }
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(new CreatorMotivation());
        motivations.add(new GrazerMotivation());
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new BasicAttributes(1, "Agent 1", 0, Color.red, 1, 1, 1, 16, 2, 96, 20, 2, 5), new BasicScores(16, 6, 0, 16, 6, 32, 0), motivations), 0);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new BasicAttributes(1, "Agent 2", 1, Color.orange, 1, 1, 1, 16, 2, 96, 20, 2, 5), new BasicScores(16, 6, 0, 16, 6, 32, 0), motivations), 1);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new BasicAttributes(1, "Agent 3", 2, Color.yellow, 1, 1, 1, 16, 2, 96, 20, 2, 5), new BasicScores(16, 6, 0, 16, 6, 32, 0), motivations), 2);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new BasicAttributes(1, "Agent 4", 3, Color.blue, 1, 1, 1, 16, 2, 96, 20, 2, 5), new BasicScores(16, 6, 0, 16, 6, 32, 0), motivations), 3);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new BasicAttributes(1, "Agent 5", 4, Color.magenta, 1, 1, 1, 16, 2, 96, 20, 2, 5), new BasicScores(16, 6, 0, 16, 6, 32, 0), motivations), 4);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new BasicAttributes(1, "Agent 6", 5, Color.cyan, 1, 1, 1, 16, 2, 96, 20, 2, 5), new BasicScores(16, 6, 0, 16, 6, 32, 0), motivations), 5);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new BasicAttributes(1, "Agent 7", 6, new Color(255, 102, 0), 1, 1, 1, 16, 2, 96, 20, 2, 5), new BasicScores(16, 6, 0, 16, 6, 32, 0), motivations), 6);
        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new BasicAttributes(1, "Agent 8", 7, new Color(180, 0, 190), 1, 1, 1, 16, 2, 96, 20, 2, 5), new BasicScores(16, 6, 0, 16, 6, 32, 0), motivations), 7);

        editingAgentIndex = 0;
    }

    public void setEditingAgentIndex(int index) {
        editingAgentIndex = index;
    }

    public Agent getEditingAgent() {
        return activeAgents.getAgent(editingAgentIndex);
    }

    public int getEditingAgentIndex() {
        return editingAgentIndex;
    }

    public AgentSettings getEditingAgentSettings() {
        return new AgentSettings(activeAgents.getAgent(editingAgentIndex).getAttributes());
    }

    public void setEditingAgentSettings(AgentSettings agentSettings) {
        getAgent(editingAgentIndex).setAttributes(agentSettings.getAgentAttributes());
    }

    public ArrayList<Agent> getActiveAgents() {
        return activeAgents.getActiveAgents();
    }

    public Agent getAgent(int index) {
        return activeAgents.getAgent(index);
    }

    public String[] getAgentNames() {
        String[] names = new String[8];
        for (int i = 0; i < 8; i++) {
            names[i] = activeAgents.getAgent(i).getAttributes().getName();
        }
        return names;
    }

    public int getWeight() {
        int weight = 0;
        for (Agent agent : activeAgents.getActiveAgents()) {
            weight += agent.getAttributes().getSpawningWeight();
        }
        return weight;
    }


}
