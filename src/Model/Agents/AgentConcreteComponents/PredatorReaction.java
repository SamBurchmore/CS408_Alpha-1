package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseReaction;
import Model.Agents.AgentInterfaces.Attributes;
import Model.Agents.AgentInterfaces.Motivations;
import Model.Agents.AgentInterfaces.Scores;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentType;
import Model.Agents.AgentStructs.AgentVision;

import java.awt.image.ColorConvertOp;
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
        ArrayList<AgentDecision> matesInRange = new ArrayList<>();
        ArrayList<AgentDecision> preyInRange = new ArrayList<>();
        for (AgentVision currentAV : agentVision) {
            if (currentAV.isInRange()) {
                if (currentAV.isOccupied()) {
                    if (super.getMotivations().toCreate(agentScores) == 1) {
                        if (currentAV.getAgentAttributes().getType().equals(agentAttributes.getType())) {
                            agentDecision.setLocation(currentAV.getLocation());
                            agentDecision.setAgentAction(AgentAction.CREATE);
                            matesInRange.add(agentDecision);
                        }
                    }
                    else if (super.getMotivations().toAttack(agentScores) == 1) {
                        agentDecision.setLocation(currentAV.getLocation());
                        agentDecision.setAgentAction(AgentAction.ATTACK);
                        preyInRange.add(agentDecision);
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

