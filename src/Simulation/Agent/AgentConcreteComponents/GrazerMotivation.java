package Simulation.Agent.AgentConcreteComponents;

import Simulation.Agent.AgentBaseComponents.BaseMotivation;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentInterfaces.Motivation;
import Simulation.Agent.AgentInterfaces.Scores;
import Simulation.Agent.AgentStructs.AgentAction;
import Simulation.Agent.AgentStructs.AgentDecision;
import Simulation.Agent.AgentStructs.AgentVision;

/**
 * Motivates agents to find free tiles with energy.
 * @author Sam Burchmore
 * @version 1.0a
 * @since 1.0a
 */
public class GrazerMotivation extends BaseMotivation {

    public GrazerMotivation(int bias, int weight) {
        super(bias, weight);
    }

    @Override
    public AgentDecision run(AgentVision tile, Attributes attributes, Scores scores) {
        if (!tile.isOccupied() || tile.getOccupantAttributes().getSize() < attributes.getSize()) { // Grazer motivation does not motivate agent to travel to occupied tiles
            if (tile.getEnergyLevel() > 0) {
                // Tile is not occupied and has food, set decision to GRAZE and score to 10
                return new AgentDecision(tile.getLocation(), AgentAction.GRAZE, (super.getBias() + Math.min(tile.getEnergyLevel(), attributes.getEatAmount()) * super.getWeight()));
            }
            // Tile is not occupied and has no food, set decision to MOVE and score to 1 (tiles food level)
            return new AgentDecision(tile.getLocation(), AgentAction.MOVE, 1);
        }
        // Tile is occupied with a larger or equally sized agent, set decision to NONE and score to -1
        return new AgentDecision(null, AgentAction.NONE, -1);
    }

    @Override
    public int getCode() {
        return 1;
    }

    @Override
    public Motivation copy() {
        return new GrazerMotivation(super.getBias(), super.getWeight());
    }

}