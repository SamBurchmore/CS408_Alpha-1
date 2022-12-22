package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentVision;

import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseReaction implements Reaction {

    private Motivations motivations;

    public BaseReaction(Motivations motivations_) {
        this.motivations = motivations_;
    }

    @Override
    public AgentDecision react(ArrayList<AgentVision> agentVision, Attributes agentAttributes, Scores agentScores) {
        Collections.shuffle(agentVision);
        ArrayList<AgentVision> emptyViewsInRange = new ArrayList<>();
        AgentDecision agentDecision = new AgentDecision();
        for (AgentVision currentAV : agentVision) {
            if (currentAV.isInRange() && !currentAV.isOccupied()) {
                emptyViewsInRange.add(currentAV);
            }
        }
        // Set default decision
        if (emptyViewsInRange.size() > 0) {
            Collections.shuffle(emptyViewsInRange);
            agentDecision.setLocation(emptyViewsInRange.get(0).getLocation());
            agentDecision.setAgentAction(AgentAction.MOVE);
        }
        return agentDecision;
    }

    protected static AgentDecision getRandomDecision(ArrayList<AgentVision> agentViews, AgentAction agentAction) {
        AgentDecision ad = new AgentDecision();
        if (agentViews.size() > 0) {
            Collections.shuffle(agentViews);
            ad.setLocation(agentViews.get(0).getLocation());
            ad.setAgentAction(agentAction);
        }
        return ad;
    }

    public Motivations getMotivations() {
        return this.motivations;
    }

    public void setMotivations(Motivations motivations) {
        this.motivations = motivations;
    }
}
