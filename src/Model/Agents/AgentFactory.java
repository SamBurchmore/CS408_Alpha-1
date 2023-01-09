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
                return new PreyAgent(location, Color.blue, new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes(1, 15, 1, 2, AgentType.PREY), new BasicScores(9, 6, 0, 9, 6, 12, 1));
            case PREDATOR:
                return new PredatorAgent(location, Color.red, new PredatorReaction(new PredatorMotivations()), new BasicVision(), new BasicAttributes(1, 25, 1, 2, AgentType.PREDATOR), new BasicScores(6, 8, 0, 6, 20, 9, 4));
            default:
                throw new IllegalArgumentException("Unknown agent type: " + agentType);
        }
    }
}
