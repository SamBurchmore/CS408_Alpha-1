package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseMotivations;
import Model.Agents.AgentInterfaces.Scores;

public class PreyMotivations extends BaseMotivations {

    public boolean toCreate(Scores agentScores) {
        // If the agents hunger is more than half, then it wants to breed.
        //System.out.println(agentScores.getCreationCounter());
        //System.out.println(agentScores.getCreationCounter() <= 0 && agentScores.getHunger() > agentScores.getMAX_HUNGER() / 2);
        return (agentScores.getCreationCounter() <= 0 );
        //return false;
    }

    @Override
    public boolean toAttack(Scores agentScores_) {
        return false;
    }

}