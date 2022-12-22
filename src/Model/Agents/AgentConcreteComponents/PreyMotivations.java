package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseMotivations;
import Model.Agents.AgentInterfaces.Scores;

public class PreyMotivations extends BaseMotivations {

    @Override
    public boolean toCreate(Scores agentScores) {
        return (agentScores.getCreationCounter() <= 0 && agentScores.getHunger() >= agentScores.getMAX_HUNGER() / 2);
    }

    @Override
    public boolean toAttack(Scores agentScores_) {
        return false;
    }

}