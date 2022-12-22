package Model.Agents;

import Model.Agents.AgentConcreteComponents.*;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentType;
import Model.Environment.Location;

import java.awt.*;

public class AgentFactory {

    public Agent createAgent(AgentType agentType, Location location) {
        switch (agentType) {
            case PREY:
                return new PreyAgent(location, Color.blue, new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes(1, 15, 1, 1, AgentType.PREY), new BasicScores(6, 6, 0, 6, 6, 4, 1));
            case PREDATOR:
                return new PredatorAgent(location, Color.red, new PredatorReaction(new PredatorMotivations()), new BasicVision(), new BasicAttributes(1, 25, 1, 4, AgentType.PREDATOR), new BasicScores(12, 12, 0, 12, 20, 6, 2));
            default:
                throw new IllegalArgumentException("Unknown agent type: " + agentType);
        }
    }
}
