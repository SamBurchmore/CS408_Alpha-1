package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseAgent;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentModelUpdate;
import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.Location;
import Model.Environment.Environment;
import Model.Environment.EnvironmentTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class PreyAgent extends BaseAgent {

    public PreyAgent(Location location_, Color agentColor_, Reaction reaction_, Vision vision_, Attributes attributes_, Scores scores_) {
        super(location_, agentColor_, reaction_, vision_, attributes_, scores_);
    }

    public PreyAgent(Location location_, Agent parentA, Agent parentB) {
        super(location_, parentA, parentB);
    }

    @Override
    public AgentModelUpdate run(Environment environment) {

        AgentDecision agentDecision = super.liveDay(environment);
        if (super.isDead()) {
            return new AgentModelUpdate(null, new ArrayList<Agent>());
        }
        ArrayList<Agent> childAgents = new ArrayList<>();
        int eatAmount = this.graze(environment.getTile(super.getLocation()));

        if (!agentDecision.isNull()) {
            if (agentDecision.getAgentAction().equals(AgentAction.MOVE)) {
                super.move(agentDecision.getLocation());
            }
            if (agentDecision.getAgentAction().equals(AgentAction.CREATE) && !environment.emptyAdjacent(this.getLocation()).isEmpty()) {
                childAgents = this.create(agentDecision.getLocation(), environment);
            }
        }
        return new AgentModelUpdate(this, childAgents, eatAmount);
    }

    @Override
    public ArrayList<Agent> create(Location parentBLocation, Environment environment_) {
        ArrayList<Location> childLocations = environment_.emptyAdjacent(super.getLocation());
        ArrayList<Agent> childAgents = new ArrayList<>();
        Collections.shuffle(childLocations);
        for (Location childLocation : childLocations.subList(0, childLocations.size() / 2)) {
            Agent child = this.combine(environment_.getTile(parentBLocation).getOccupant(), childLocation);
            child.getScores().setHunger(child.getScores().getMAX_HUNGER() / 2);
            child.getScores().setCreationCounter(5);
            childAgents.add(child);
        }
        return childAgents;
}

    @Override
    public Agent combine(Agent parentB, Location childLocation) {
        super.getScores().setCreationCounter(super.getScores().getCreationDelay());
        Agent newAgent = new PreyAgent(childLocation, this, parentB);
        return newAgent;
    }

    @Override
    public Object copy() {
        return null;
    }


    public int graze(EnvironmentTile environmentTile) {
        if (environmentTile.getFoodLevel() <= 0) {
            return 0;
        }
        if (environmentTile.getFoodLevel() >= super.getAttributes().getEatAmount()) {
            super.getScores().setHunger(super.getScores().getHunger() + super.getAttributes().getEatAmount());
            return super.getAttributes().getEatAmount();
        }
        return environmentTile.getFoodLevel();
    }

}
