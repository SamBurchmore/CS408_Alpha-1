package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseReaction;
import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivations;
import Model.Agents.AgentInterfaces.Scores;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentType;
import Model.Agents.AgentStructs.AgentVision;

import java.util.ArrayList;
import java.util.Collections;

public class PredatorReaction extends BaseReaction {

    public PredatorReaction(Motivations motivations_) {
        super(motivations_);
    }

    @Override
    public AgentDecision react(ArrayList<AgentVision> agentVision, Attributes agentAttributes, Scores agentScores) {
        AgentDecision agentDecision = new AgentDecision();
        ArrayList<AgentDecision> emptyViewsInRange = new ArrayList<>();
        ArrayList<AgentDecision> occupiedViewsInRange = new ArrayList<>();
        for (AgentVision currentAV : agentVision) {
            if (currentAV.isOccupied() && currentAV.isInRange()) {
                if (super.getMotivations().toCreate(agentScores)) {
                    agentDecision.setLocation(currentAV.getLocation());
                    agentDecision.setAgentAction(AgentAction.CREATE);
                    occupiedViewsInRange.add(agentDecision);
                }
                if (super.getMotivations().toAttack(agentScores)) {
                    if (currentAV.getAgentAttributes().getType().equals(AgentType.PREY)) {
                        agentDecision.setLocation(currentAV.getLocation());
                        agentDecision.setAgentAction(AgentAction.ATTACK);
                        occupiedViewsInRange.add(agentDecision);
                        //System.out.println("maybe attack");
                    }
                }
            }
            else if (currentAV.isInRange()) {
                agentDecision.setLocation(currentAV.getLocation());
                agentDecision.setAgentAction(AgentAction.MOVE);
                emptyViewsInRange.add(agentDecision);
            }
        }
        if (!occupiedViewsInRange.isEmpty()) {
            Collections.shuffle(occupiedViewsInRange);
            //System.out.println("attack");
            return occupiedViewsInRange.get(0);
        }
        if (!emptyViewsInRange.isEmpty()) {
            Collections.shuffle(emptyViewsInRange);
                //System.out.println("moving to good food tile");
                return emptyViewsInRange.get(0);
            }
        agentDecision.setAgentAction(AgentAction.NONE);
        agentDecision.setLocation(null);
        return agentDecision;
    }
}
