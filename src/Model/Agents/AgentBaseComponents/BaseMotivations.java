package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Motivations;
import Model.Agents.AgentInterfaces.Scores;

public abstract class BaseMotivations implements Motivations {

    @Override
    public int toCreate(Scores agentScores) {
        if (agentScores.getCreationCounter() <= 0 && agentScores.getHunger() >= agentScores.getMAX_HUNGER()) {
            return 1;
        };
        return 0;
    }

}
