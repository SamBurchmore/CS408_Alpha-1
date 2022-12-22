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
                return new PreyAgent(location, Color.blue, new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes(3, 15, 2, 3, AgentType.PREY), new BasicScores(6, 6, 0, 6, 6, 9, 3));
            case PREDATOR:
                return new PredatorAgent(location, Color.red, new PredatorReaction(new PredatorMotivations()), new BasicVision(), new BasicAttributes(3, 25, 3, 6, AgentType.PREDATOR), new BasicScores(12, 12, 0, 12, 12, 12, 3));
            default:
                throw new IllegalArgumentException("Unknown agent type: " + agentType);
        }
    }
}
