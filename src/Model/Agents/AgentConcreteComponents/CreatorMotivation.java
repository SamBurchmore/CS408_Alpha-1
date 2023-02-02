package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivation;
import Model.Agents.AgentInterfaces.Scores;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentVision;

import java.io.Serializable;

public class CreatorMotivation implements Motivation, Serializable {

    int motivationCode = 0;

    @Override
    public AgentDecision run(AgentVision tile, Attributes attributes, Scores scores) {
        if (tile.isOccupied()) {
            if (tile.getAgentAttributes().getCode() == attributes.getCode() && scores.getHunger() > attributes.getEnergyCapacity() / 2 && scores.getAge() >= attributes.getCreationAge() && scores.getCreationCounter() <= 0) {
                // Tile is occupied, and it's occupant is the same species, set the decision to CREATE and the score 10
                return new AgentDecision(tile.getLocation(), AgentAction.CREATE, 10);
            }
            // Tile is occupied but its occupant is a different species, set decision to NONE and score to -10
            return new AgentDecision(null, AgentAction.NONE, -10);
        }
        // Tile is not occupied, set decision to MOVE and score to 1
        return new AgentDecision(tile.getLocation(), AgentAction.MOVE, 1);
    }

    @Override
    public Motivation copy() {
        return new CreatorMotivation();
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
