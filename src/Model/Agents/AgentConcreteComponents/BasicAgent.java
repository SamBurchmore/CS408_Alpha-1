package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseAgent;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.*;
import Model.Environment.Environment;
import Model.Environment.EnvironmentTile;
import Model.Environment.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class BasicAgent extends BaseAgent {

    public BasicAgent(Location location, Color agentColor, Reaction reaction, Vision vision, Attributes attributes, Scores scores) {
        super(location, agentColor, reaction, vision, attributes, scores);
    }

    public BasicAgent(Location location, Agent parentA, Agent parentB) {
        super(location, parentA, parentB);
    }

    @Override
    public AgentModelUpdate run(Environment environment) {

        super.liveDay(environment);
        ArrayList<AgentVision> agentVision = this.getVision().lookAround(environment, this.getLocation(), this.getAttributes().getVision(), this.getAttributes().getSpeed());
        AgentUpdate agentUpdate = this.getReaction().react(agentVision, this.getAttributes(), this.getScores());

        if (super.isDead()) {
            return new AgentModelUpdate(null, new ArrayList<Agent>());
        }

        ArrayList<Agent> childAgents = new ArrayList<>();
        if (agentUpdate.getAction().equals(AgentAction.MOVE)) {
            int eatAmount = this.graze(environment.getTile(super.getLocation()));
            super.move(agentUpdate.getLocation());
            return new AgentModelUpdate(this, childAgents, eatAmount);
        }
        if (agentUpdate.getAction().equals(AgentAction.CREATE) && !environment.emptyAdjacent(this.getLocation()).isEmpty()) {
            childAgents = this.create(agentUpdate.getLocation(), environment);
            return new AgentModelUpdate(this, childAgents, 0);
        }
        return
    }

    @Override
    public ArrayList<Agent> create(Location parentBLocation, Environment environment_) {
        ArrayList<Location> childLocations = environment_.emptyAdjacent(super.getLocation());
        ArrayList<Agent> childAgents = new ArrayList<>();
        Collections.shuffle(childLocations);
        for (Location childLocation : childLocations.subList(0, childLocations.size() / 2)) {
            Agent child = this.combine(environment_.getTile(parentBLocation).getOccupant(), childLocation);
            childAgents.add(child);
        }
        return childAgents;
}

    @Override
    public Agent combine(Agent parentB, Location childLocation) {
        super.getScores().setCreationCounter(super.getAttributes().getCreationDelay());
        Agent newAgent = new BasicAgent(childLocation, this, parentB);
        super.getScores().setHunger(super.getAttributes().getMaxEnergy() - super.getAttributes().getMaxEnergy() / 8);
        newAgent.getScores().setHunger(newAgent.getAttributes().getMaxEnergy() / 8);
        newAgent.getScores().setCreationCounter(newAgent.getAttributes().getMaxAge() / 2);
        return newAgent;
    }


    public int graze(EnvironmentTile environmentTile) {
        if (environmentTile.getFoodLevel() <= 0) {
            return 0;
        }
        if (environmentTile.getFoodLevel() >= super.getAttributes().getEatAmount()) {
            super.getScores().setHunger(super.getScores().getHunger() + super.getAttributes().getEatAmount());
            return super.getAttributes().getEatAmount();
        }
        super.getScores().setHunger(super.getScores().getHunger() + environmentTile.getFoodLevel());
        return environmentTile.getFoodLevel();
    }

    public BasicAgent copy() {
        return new BasicAgent(this.getLocation(), this, this);
    }

}
