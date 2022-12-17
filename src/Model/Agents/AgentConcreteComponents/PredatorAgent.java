package Model.Agents.AgentConcreteComponents;

import Model.Agents.AgentBaseComponents.BaseAgent;
import Model.Agents.AgentInterfaces.*;
import Model.Agents.AgentStructs.*;
import Model.Environment.Location;
import Model.Environment.Environment;

import java.awt.*;
import java.util.ArrayList;

public class PredatorAgent extends BaseAgent {

    public PredatorAgent(Location location_, Color agentColor_, Reaction reaction_, Vision vision_, Attributes attributes_, Scores scores_) {
        super(location_, agentColor_, reaction_, vision_, attributes_, scores_);
    }

    public PredatorAgent(Location location_, Agent parentA, Agent parentB) {
        super(location_, parentA, parentB);
    }

    @Override
    public Environment run(Environment environment_) {

        super.liveDay();

        if (super.isDead()) {
            environment_.setTileAgent(super.getLocation(), null);
            //System.out.println("die");
            return environment_;
        }

        ArrayList<AgentVision> agentSight = super.getVision().lookAround(environment_, super.getLocation(), super.getAttributes().getVision(), super.getAttributes().getSpeed());
        AgentDecision agentDecision = super.getReaction().react(agentSight, super.getAttributes(), super.getScores());

        if (agentDecision.getAgentAction().equals(AgentAction.MOVE)) {
            environment_ = super.move(agentDecision.getLocation(), environment_);
        }
        if (agentDecision.getAgentAction().equals(AgentAction.CREATE)) {
            environment_ = super.create(agentDecision.getLocation(), environment_);
        }
        if (agentDecision.getAgentAction().equals(AgentAction.ATTACK)) {
            this.predate(environment_.getTile(agentDecision.getLocation()).getOccupant().getAttributes().getSize());
            environment_ = super.move(agentDecision.getLocation(), environment_);
        }

        return environment_;
    }

    @Override
    public Agent combine(Agent parentB, Location childLocation) {
        super.getScores().setCreationCounter(super.getScores().getCreationDelay());
        return new PredatorAgent(childLocation, this, parentB);
    }

    public void predate(int preySize) {
        super.getScores().setHunger(super.getScores().getHunger() + 500);
    }

    @Override
    public void liveDay() {
        super.getScores().setHunger((super.getScores().getHunger() - 200));
        super.getScores().setAge(super.getScores().getAge()+1);
        super.getScores().setCreationCounter((super.getScores().getCreationCounter()-1));
        if (super.getScores().getHunger() >= 50) {
            super.getScores().setHealth(super.getScores().getHealth() + 15);
        }
        if (super.getScores().getHunger() < 250) {
            super.getScores().setHealth(super.getScores().getHealth() - 50);
        }
    }

}
