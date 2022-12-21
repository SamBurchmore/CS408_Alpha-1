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
                return new PreyAgent(location, Color.blue, new PreyReaction(new PreyMotivations()), new BasicVision(), new BasicAttributes(6, 15, 6, 1, AgentType.PREY), new BasicScores(6, 6, 0, 6, 6, 10, 0));
            case PREDATOR:
                return new PredatorAgent(location, Color.red, new PredatorReaction(new PredatorMotivations()), new BasicVision(), new BasicAttributes(1, 25, 1, 30, AgentType.PREDATOR), new BasicScores(500, 200, 0, 500, 500, 150, 100));
            default:
                throw new IllegalArgumentException("Unknown agent type: " + agentType);
        }
    }
}
