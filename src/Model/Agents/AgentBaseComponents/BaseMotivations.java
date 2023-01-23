package Model.Agents.AgentBaseComponents;

import Model.Agents.AgentInterfaces.Motivations;

public abstract class BaseMotivations implements Motivations {

    @Override
    public int toCreate(Scores agentScores) {
        if (agentScores.getCreationCounter() <= 0 && agentScores.getHunger() >= agentScores.getMAX_HUNGER()) {
            return 1;
        };
        return 0;
    }

}
