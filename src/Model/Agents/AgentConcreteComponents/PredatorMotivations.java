package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseMotivations;
import Model.Agents.AgentInterfaces.Scores;

public class PredatorMotivations extends BaseMotivations {

    public int toCreate(Scores agentScores) {
        if (agentScores.getCreationCounter() <= 0 && agentScores.getHunger() >= agentScores.getMAX_HUNGER() / 4) {
            return 1;
        }
        return 0;
    }

    public int toAttack(Scores agentScores) {
        if (agentScores.getHunger() < agentScores.getMAX_HUNGER()) {
            return 1;
        }
        return 0;
    }
}
