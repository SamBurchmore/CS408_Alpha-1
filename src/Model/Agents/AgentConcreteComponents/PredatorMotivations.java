package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseMotivations;
import Model.Agents.AgentInterfaces.Scores;

public class PredatorMotivations extends BaseMotivations {

    public boolean toCreate(Scores agentScores) {
        // If the agents hunger is more than half, then it wants to breed.
        return (agentScores.getCreationCounter() <= 0 ) && (agentScores.getHunger() > 250);
    }

    public boolean toAttack(Scores agentScores) {
        // If the agents hunger is less than its max, then it wants to eat.
        return agentScores.getHunger() < 250;
    }

}
