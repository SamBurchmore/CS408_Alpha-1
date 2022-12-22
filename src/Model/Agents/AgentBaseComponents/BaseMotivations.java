package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Motivations;
import Model.Agents.AgentInterfaces.Scores;

public abstract class BaseMotivations implements Motivations {

    @Override
    public boolean toCreate(Scores agentScores) {
        return (agentScores.getCreationCounter() <= 0 && agentScores.getHunger() >= agentScores.getMAX_HUNGER());
    }

}
