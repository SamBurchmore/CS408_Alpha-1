package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivation;
import Model.Agents.AgentInterfaces.Scores;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentVision;

public class CreatorMotivation implements Motivation {

    @Override
    public AgentDecision run(AgentVision tile, Attributes attributes, Scores scores) {
        if (tile.isOccupied()) {
            if (tile.getAgentAttributes().getCode() == attributes.getCode()) {
                // Tile is occupied, and it's occupant is the same species, set the decision to CREATE and the score to 10 * (agents missing energy / agent size)
                return new AgentDecision(tile.getLocation(), AgentAction.CREATE, 10 * ((attributes.getEnergyCapacity() - scores.getHunger()) / attributes.getSize()));
            }
            // Tile is occupied but its occupant is a different species, set decision to NONE and score to -10
            return new AgentDecision(null, AgentAction.NONE, -10);
        }
        // Tile is not occupied, set decision to MOVE and score to 1
        return new AgentDecision(tile.getLocation(), AgentAction.MOVE, 1);
    }

}
