package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseReaction;
import Model.Agents.AgentInterfaces.Motivations;
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
        ArrayList<AgentDecision> matesInRange = new ArrayList<>();
        for (AgentVision currentAV : agentVision) {
            if (currentAV.isInRange()) {
                if (currentAV.isOccupied()) {
                    if (super.getAgentMotivations().toCreate(agentScores) == 1) {
                        if (currentAV.getAgentAttributes().getType().equals(agentAttributes.getType())) {
                            agentDecision.setLocation(currentAV.getLocation());
                            agentDecision.setAgentAction(AgentAction.CREATE);
                            matesInRange.add(agentDecision);
                        }
                    }
                }
                else {
                    agentDecision.setLocation(currentAV.getLocation());
                    agentDecision.setAgentAction(AgentAction.MOVE);
                    emptyViewsInRange.add(agentDecision);
                }
            }
        }
        if (!matesInRange.isEmpty()) {
            Collections.shuffle(matesInRange);
            return matesInRange.get(0);
        }
        if (!emptyViewsInRange.isEmpty()) {
            Collections.shuffle(emptyViewsInRange);
            return emptyViewsInRange.get(0);
        }
        agentDecision.setAgentAction(AgentAction.NONE);
        agentDecision.setLocation(null);
        return agentDecision;
    }
}
