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
    public AgentModelUpdate run(Environment environment_) {

        this.liveDay();
        if (this.isDead()) {
            return new AgentModelUpdate(null, new ArrayList<Agent>());
        }

        int eatAmount = this.graze(environment_.getTile(super.getLocation()));
        ArrayList<AgentVision> agentSight = super.getVision().lookAround(environment_, super.getLocation(), super.getAttributes().getVision(), super.getAttributes().getSpeed());
        AgentDecision agentDecision = super.getReaction().react(agentSight, super.getAttributes(), super.getScores());
        ArrayList<Agent> childAgents = new ArrayList<>();

        if (!agentDecision.isNull()) {
            if (agentDecision.getAgentAction().equals(AgentAction.MOVE)) {
                super.move(agentDecision.getLocation());
            }
            if (agentDecision.getAgentAction().equals(AgentAction.CREATE)) {
                childAgents = this.create(agentDecision.getLocation(), environment_);
            }
        }
        return new AgentModelUpdate(this, childAgents, eatAmount);
    }

    @Override
    public ArrayList<Agent> create(Location parentBLocation, Environment environment_) {
        ArrayList<Location> childLocations = environment_.emptyAdjacent(super.getLocation());
        ArrayList<Agent> childAgents = new ArrayList<>();
        Collections.shuffle(childLocations);
        for (Location childLocation : childLocations.subList(0, childLocations.size()/2)) {
            childAgents.add(this.combine(environment_.getTile(parentBLocation).getOccupant(), childLocation));
        }
        return childAgents;
}

    @Override
    public Agent combine(Agent parentB, Location childLocation) {
        super.getScores().setCreationCounter(super.getScores().getCreationDelay());
        Agent newAgent = new PreyAgent(childLocation, this, parentB);
        return newAgent;
    }


    public int graze(EnvironmentTile environmentTile) {
        //System.out.println(environmentTile.getFoodLevel());
        if (environmentTile.getFoodLevel() <= 0) {
            return 0;
        }
        if (environmentTile.getFoodLevel() >= super.getAttributes().getEatAmount()) {
            super.getScores().setHunger(super.getScores().getHunger() + super.getAttributes().getEatAmount());
            //System.out.println(super.getAttributes().getEatAmount());
            return super.getAttributes().getEatAmount();
        }
        //System.out.println(environmentTile.getFoodLevel());
        return environmentTile.getFoodLevel();
    }

    @Override
    public void liveDay() {
        super.getScores().setHunger((super.getScores().getHunger() - 1));
        super.getScores().setAge(super.getScores().getAge()+1);
        super.getScores().setCreationCounter((super.getScores().getCreationCounter() - 1));
        //System.out.println("Hunger: " + super.getScores().getHunger() + ", Health: " + super.getScores().getHealth() + ", Age: " + super.getScores().getAge() + ", (" + super.getLocation().getX() + "," + super.getLocation().getY() + ")");
    }

    @Override
    public boolean isDead() {
        return this.getScores().getHunger() <= 0 || this.getScores().getAge() >= this.getScores().getMAX_AGE();
    }

}
