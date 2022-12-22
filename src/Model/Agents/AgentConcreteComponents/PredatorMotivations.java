package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseMotivations;
import Model.Agents.AgentInterfaces.Scores;

public class PredatorMotivations extends BaseMotivations {

    public int toCreate(Scores agentScores) {
        // If the agents hunger is more than half, then it wants to breed.
        if (agentScores.getCreationCounter() > 0) {
            return 0;
        }
        double modifier = agentScores.getMAX_HUNGER() * 2 + agentScores.getHunger() * 2;
        return (int) modifier;
    }

    public int toAttack(Scores agentScores) {
        // If the agents hunger is less than its max, then it wants to eat.
        if (agentScores.getHunger() >= agentScores.getMAX_HUNGER()) {
            return 0;
        }
        double modifier = agentScores.getMAX_HUNGER() * 4 - agentScores.getHunger();
        return (int) modifier;
    }

}
