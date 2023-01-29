package Model.AgentEditor;

import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.Agent;
import Model.Agents.AgentInterfaces.Attributes;
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
        for (int i = 0; i < AGENT_NUMBER; i++) { //                                                                                                                                         (String name, int code, Color color, int visionRange, int movementRange, int size, int energyCapacity, int eatAmount, int lifespan, int creationAge, int creationAmount, int creationDelay) {

            activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent " + (i+1), i, new Color(245 - i * (i * 5), 0, i * (i * 5)), 1, 1, 1, 16, 2, 32, 5, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), i);
        }
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent 1", 0, Color.red, 1, 1, 1, 16, 2, 32, 5, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), 0);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent 2", 1, Color.orange, 1, 1, 1, 16, 2, 36, 7, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), 1);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent 3", 2, Color.yellow, 1, 1, 1, 16, 2, 40, 9, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), 2);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent 4", 3, Color.blue, 1, 1, 1, 16, 2, 44, 11, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), 3);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent 5", 4, Color.magenta, 1, 1, 1, 16, 2, 48, 13, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), 4);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent 6", 5, Color.cyan, 1, 1, 1, 16, 2, 52, 15, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), 5);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent 7", 6, Color.pink, 1, 1, 1, 16, 2, 56, 17, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), 6);
//        activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent 8", 7, Color.darkGray, 1, 1, 1, 16, 2, 60, 19, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), 7);

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


}
