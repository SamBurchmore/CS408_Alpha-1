package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivation;
import Model.Agents.AgentInterfaces.Scores;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentVision;

import java.io.Serializable;

public class PredatorMotivation implements Motivation, Serializable {

    int motivationCode = 2;

    @Override
    public AgentDecision run(AgentVision tile, Attributes attributes, Scores scores) {
        if (tile.isOccupied()) { // Predator motivation motivates agent to move to occupied tiles
            if (tile.getAgentAttributes().getSize() <= tile.getAgentAttributes().getSize() && tile.getAgentAttributes().getCode() != attributes.getCode()) {
                // Tile is occupied, its occupant is smaller than the agent, and it's a different 'species' (code is different),
                // set decision to PREDATE and its score to the occupants size multiplied by how much energy its missing
                return new AgentDecision(tile.getLocation(), AgentAction.PREDATE, 10);
            }
            // Tile is occupied but its occupant is either larger or the same species, set decision to NONE and its score to -10
            return new AgentDecision(null, AgentAction.NONE, -10);
        }
        // Tile is not occupied, set decision to MOVE and score to 1
        return new AgentDecision(tile.getLocation(), AgentAction.MOVE, 1);
    }

    @Override
    public Motivation copy() {
        return new PredatorMotivation();
    }

    @Override
    public boolean equals(Motivation motivation) {
        return motivation.getCode() == this.getCode();
    }

    @Override
    public int getCode() {
        return motivationCode;
    }

}
