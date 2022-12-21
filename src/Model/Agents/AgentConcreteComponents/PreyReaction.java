package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseReaction;
import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivations;
import Model.Agents.AgentInterfaces.Scores;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentVision;

import java.util.ArrayList;
import java.util.Collections;

public class PreyReaction extends BaseReaction {

    public PreyReaction(Motivations motivations_) {
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
                    if (currentAV.getAgentAttributes().getType().equals(agentAttributes.getType())) {
                        agentDecision.setLocation(currentAV.getLocation());
                        agentDecision.setAgentAction(AgentAction.CREATE);
                        occupiedViewsInRange.add(agentDecision);
                    }
                }
            }
        }
        if (!occupiedViewsInRange.isEmpty()) {
            Collections.shuffle(occupiedViewsInRange);
            return occupiedViewsInRange.get(0);
        }
        for (AgentVision currentAV : agentVision) {
            if (currentAV.isInRange() && !currentAV.isOccupied()) {
                agentDecision.setLocation(currentAV.getLocation());
                agentDecision.setAgentAction(AgentAction.MOVE);
                if (currentAV.getFoodLevel() > agentAttributes.getEatAmount() * 2) {
                    agentDecision.setRank(1);
                }
                emptyViewsInRange.add(agentDecision);
            }
        }
        if (!emptyViewsInRange.isEmpty()) {
            ArrayList<AgentDecision> topChoices = new ArrayList<>();
            for (AgentDecision emptyView : emptyViewsInRange) {
                if (emptyView.getRank() > 0) {
                    topChoices.add(emptyView);
                }
            }
            if (!topChoices.isEmpty()) {
                Collections.shuffle(topChoices);
                //System.out.println("moving to good food tile");
                return topChoices.get(0);
            }
            Collections.shuffle(emptyViewsInRange);
            //System.out.println("moving to tile");
            return emptyViewsInRange.get(0);
        }
        agentDecision.setAgentAction(AgentAction.NONE);
        agentDecision.setLocation(null);
        return agentDecision;
    }
}
