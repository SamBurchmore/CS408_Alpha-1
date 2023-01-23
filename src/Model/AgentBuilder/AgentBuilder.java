package Model.AgentBuilder;

import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.Agent;
import Model.Agents.AgentStructs.AgentType;
import Model.Environment.Location;

import java.awt.*;
import java.util.ArrayList;

public class AgentBuilder {

    private final int _agentNumber_ = 8;

    private ActiveAgents activeAgents;

    private Agent openAgent;

    public AgentBuilder() {
        activeAgents = new ActiveAgents();
        for (int i = 0; i < _agentNumber_; i++) {
            activeAgents.addAgent(new BasicAgent(new Location(-1, -1), new Color(0, 156, 255), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes(1, 1, 1 , 2, AgentType.PREY), new BasicScores(16, 6, 0, 16, 6, 32, 0)), i);
        }
        openAgent = activeAgents.getAgent(0);
    }

    public void setOpenAgent(int index) {
        openAgent = activeAgents.getAgent(index);
    }

    public Agent getOpenAgent() {
        return openAgent;
    }

    public AgentSettings getOpenAgentView(int index) {
        return new AgentSettings(openAgent.getScores().getMAX_AGE(), openAgent.getScores().getMAX_HUNGER(), openAgent.getAttributes().getEatAmount(), openAgent.getAttributes().getVision(), openAgent.getAttributes().getSpeed(), openAgent.getColor());
    }

    public void buildAgent(AgentSettings agentSettings, int index) {
        activeAgents.setAgent(new BasicAgent(new Location(-1, -1), agentSettings.getColor(), new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes(agentSettings.getMovementRange(), 1, agentSettings.getVisionRange(), agentSettings.getEnergyAmount(), AgentType.PREY), new BasicScores(agentSettings.getEnergyCapacity(), 1, 0, agentSettings.getEnergyCapacity(), 1, agentSettings.getLifespan(), 0)), index);
    }

    public Agent getAgent(int index) {
        return activeAgents.getAgent(index);
    }

    public ArrayList<Agent> getActiveAgents() {
        return activeAgents.getActiveAgents();
    }
}
