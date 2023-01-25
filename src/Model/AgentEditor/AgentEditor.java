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

            activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new Color(0, 156, 255), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes("Agent " + (i+1), i, Color.white, 1, 1, 1, 10, 2, 10, 5, 4, 1), new BasicScores(16, 6, 0, 16, 6, 32, 0)), i);
        }
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
        int code = activeAgents.getAgent(editingAgentIndex).getAttributes().getCode(); // Store the agents code here, should either move out of attributes are make editable
        activeAgents.getAgent(editingAgentIndex).setAttributes(agentSettings.getAgentAttributes());
        activeAgents.getAgent(editingAgentIndex).getAttributes().setCode(code);
    }

    public ArrayList<Agent> getActiveAgents() {
        return activeAgents.getActiveAgents();
    }

    public Agent getAgent(int index) {
        return activeAgents.getAgent(index);
    }
}
