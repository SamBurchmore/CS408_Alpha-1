package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseReaction;
import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivations;
import Model.Agents.AgentInterfaces.Scores;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentUpdate;
import Model.Agents.AgentStructs.AgentVision;

import java.util.ArrayList;
import java.util.Collections;

public class PreyReaction extends BaseReaction {

    public PreyReaction(Motivations motivations_) {
        super(motivations_);
    }

    @Override
    public AgentUpdate react(ArrayList<AgentVision> agentVision, Attributes agentAttributes, Scores agentScores) {
        AgentUpdate agentUpdate = new AgentUpdate();
        ArrayList<AgentUpdate> emptyViewsInRange = new ArrayList<>();
        ArrayList<AgentUpdate> matesInRange = new ArrayList<>();
        for (AgentVision currentAV : agentVision) {
            if (currentAV.isInRange()) {
                if (currentAV.isOccupied()) {
                    if (super.getAgentMotivations().toCreate(agentScores) == 1) {
                        if (currentAV.getAgentAttributes().getType().equals(agentAttributes.getType())) {
                            agentUpdate.setLocation(currentAV.getLocation());
                            agentUpdate.setAgentAction(AgentAction.CREATE);
                            matesInRange.add(agentUpdate);
                        }
                    }
                }
                else {
                    agentUpdate.setLocation(currentAV.getLocation());
                    agentUpdate.setAgentAction(AgentAction.MOVE);
                    emptyViewsInRange.add(agentUpdate);
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
        agentUpdate.setAgentAction(AgentAction.NONE);
        agentUpdate.setLocation(null);
        return agentUpdate;
    }
}
