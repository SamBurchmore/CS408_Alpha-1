package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivation;
import Model.Agents.AgentInterfaces.Scores;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.EnvironmentTile;

public class GrazerMotivation implements Motivation {

    @Override
    public AgentDecision run(AgentVision tile, Attributes attributes, Scores scores) {
        if (!tile.isOccupied()) { // Grazer motivation does not motivate agent to travel to occupied tiles
            if (tile.getFoodLevel() > 0) {
                // Tile is not occupied and has food, set decision to GRAZE and score to 10
                return new AgentDecision(tile.getLocation(), AgentAction.GRAZE, 10);
            }
            // Tile is not occupied and has no food, set decision to MOVE and score to 1 (tiles food level)
            return new AgentDecision(tile.getLocation(), AgentAction.MOVE, 1);
        }
        // Tile is occupied, set decision to NONE and score to -10
        return new AgentDecision(null, AgentAction.NONE, -10);
    }

    @Override
    public Motivation copy() {
        return new GrazerMotivation();
    }

}