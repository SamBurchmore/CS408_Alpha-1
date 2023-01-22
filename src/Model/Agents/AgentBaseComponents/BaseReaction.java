package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentUpdate;
import Model.Agents.AgentStructs.AgentVision;

import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseReaction implements Model.Agents.AgentInterfaces.Reaction {

    private ArrayList<Motivations> agentMotivations;

    public BaseReaction(ArrayList<Motivations> agentMotivations) {
        this.agentMotivations = agentMotivations;
    }

    @Override
    public AgentUpdate react(ArrayList<AgentVision> agentVision, Attributes agentAttributes, Scores agentScores) {
        Collections.shuffle(agentVision);
        int[] tileScores = new int[agentVision.size()];
        int i = 0;
        for (AgentVision tile : agentVision) {
            for (Motivations motivations : agentMotivations) {
                tileScores[i] += motivations.run(tile, agentAttributes, agentScores);
            }
            i++;
        }
        int chosenTile = 0;
        for (int i = 0; i < tileScores.length; i++) {
            if ()
        }
        AgentVision chosenTile = agentVision.get(Math. (tileScores))
        return new AgentUpdate();
    }

    protected static AgentUpdate getRandomDecision(ArrayList<AgentVision> agentViews, AgentAction agentAction) {
        AgentUpdate ad = new AgentUpdate();
        if (agentViews.size() > 0) {
            Collections.shuffle(agentViews);
            ad.setLocation(agentViews.get(0).getLocation());
            ad.setAgentAction(agentAction);
        }
        return ad;
    }

    public Motivations getAgentMotivations() {
        return this.agentMotivations;
    }

    public void setAgentMotivations(Motivations agentMotivations) {
        this.agentMotivations = agentMotivations;
    }
}
