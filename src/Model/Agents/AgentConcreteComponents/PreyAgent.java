package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseAgent;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.AgentAction;
import Model.Agents.AgentStructs.AgentDecision;
import Model.Agents.AgentStructs.AgentVision;
import Model.Environment.Location;
import Model.Environment.Environment;
import Model.Environment.EnvironmentTile;

import java.awt.*;
import java.util.ArrayList;

public class PreyAgent extends BaseAgent {

    public PreyAgent(Location location_, Color agentColor_, Reaction reaction_, Vision vision_, Attributes attributes_, Scores scores_) {
        super(location_, agentColor_, reaction_, vision_, attributes_, scores_);
    }

    public PreyAgent(Location location_, Agent parentA, Agent parentB) {
        super(location_, parentA, parentB);
        super.getScores().setCreationCounter(0);
    }

    @Override
    public Environment run(Environment environment_) {

        super.liveDay();
        if (super.isDead()) {
            environment_.setTileAgent(super.getLocation(), null);
            return environment_;
        }

        environment_.modifyTileFoodLevel(super.getLocation(), this.graze(environment_.getTile(super.getLocation())));
        ArrayList<AgentVision> agentSight = super.getVision().lookAround(environment_, super.getLocation(), super.getAttributes().getVision(), super.getAttributes().getSpeed());
        AgentDecision agentDecision = super.getReaction().react(agentSight, super.getAttributes(), super.getScores());

        if (!agentDecision.isNull()) {
            if (agentDecision.getAgentAction().equals(AgentAction.MOVE)) {
                environment_ = super.move(agentDecision.getLocation(), environment_);
            }
            if (agentDecision.getAgentAction().equals(AgentAction.CREATE)) {
                environment_ = this.create(agentDecision.getLocation(), environment_);
            }
        }
        //System.out.println(worldGrid_.getTile(super.getLocation()).getFoodLevel());
        environment_.modifyTileFoodLevel(super.getLocation(), -super.getAttributes().getEatAmount());

        this.liveDay();
        if (super.isDead()) {
            environment_.setTileAgent(super.getLocation(), null);
        }
        return environment_;
    }

    @Override
    public Environment create(Location parentBLocation, Environment environment_) {
        ArrayList<Location> childLocations = environment_.emptyAdjacent(super.getLocation());
        for (Location childLocation : childLocations) {
            Agent child = this.combine(environment_.getTile(parentBLocation).getOccupant(), childLocation);
            environment_.setTileAgent(childLocation, child);
    }
        return environment_;
}

    @Override
    public Agent combine(Agent parentB, Location childLocation) {
        super.getScores().setCreationCounter(super.getScores().getCreationDelay());
        return new PreyAgent(childLocation, this, parentB);
    }


    public int graze(EnvironmentTile environmentTile) {
        super.getScores().setHunger(super.getScores().getHunger() + super.getAttributes().getEatAmount());
        return super.getAttributes().getEatAmount();
    }

    @Override
    public void liveDay() {
        super.getScores().setHunger((super.getScores().getHunger() - 1));
        super.getScores().setAge(super.getScores().getAge()+1);
        super.getScores().setCreationCounter((super.getScores().getCreationCounter() - 1));
        if (super.getScores().getHunger() > 5) {
            super.getScores().setHealth(super.getScores().getHealth() + 1);
        }
        if (super.getScores().getHunger() < 3) {
            super.getScores().setHealth(super.getScores().getHealth() - 1);
        }
        //System.out.println("Hunger: " + super.getScores().getHunger() + ", Health: " + super.getScores().getHealth() + ", Age: " + super.getScores().getAge() + ", (" + super.getLocation().getX() + "," + super.getLocation().getY() + ")");
    }

}
