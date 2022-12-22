package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseMotivations;
import Model.Agents.AgentInterfaces.Scores;

public class PreyMotivations extends BaseMotivations {

    @Override
    public int toCreate(Scores agentScores) {
        if (agentScores.getCreationCounter() <= 0 && agentScores.getHunger() >= agentScores.getMAX_HUNGER() / 4) {
            return 1;
        }
        return 0;
    }

    @Override
    public int toAttack(Scores agentScores_) {
        return 0;
    }

}